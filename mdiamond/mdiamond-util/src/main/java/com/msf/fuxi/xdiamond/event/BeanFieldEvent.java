package com.msf.fuxi.xdiamond.event;

import com.msf.fuxi.xdiamond.annotation.XDiamondValue;
import com.msf.fuxi.xdiamond.init.XDiamondValueInit;
import org.apache.commons.beanutils.ConvertUtils;
import org.slf4j.Logger;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Scope;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class BeanFieldEvent implements BeanPostProcessor {

    Logger logger = org.slf4j.LoggerFactory.getLogger("BeanFieldEvent");

    public static Map<String, List<FieldEvent>> oneKeyListenerMap = new ConcurrentHashMap<String, List<FieldEvent>>();


    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }


    @SuppressWarnings(value = { "all" })
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Object obj = bean;
        if (AopUtils.isAopProxy(bean)) {
            // 如果为代理，获取代理的真实对象
            try {
                if (AopUtils.isJdkDynamicProxy(bean)) {
                    obj = getJdkDynamicProxyTargetObject(bean);
                } else {
                    obj = getCglibProxyTargetObject(bean);
                }
            } catch (Exception e) {
                logger.info("Failed to get proxy object {}", bean.getClass().getName(), e);
            }
        }
        // 判断bean是否为多例模式
        boolean isPrototype = false;
        AnnotatedGenericBeanDefinition beanDefinition = new AnnotatedGenericBeanDefinition(obj.getClass());
        if (beanDefinition.getMetadata().isAnnotated(Scope.class.getName())) {
            String value = (String) beanDefinition.getMetadata().getAnnotationAttributes(Scope.class.getName()).get("value");
            if (value.equals("prototype")) {
                isPrototype = true;
            }
        }
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(XDiamondValue.class)) {
                try {
                    Annotation annotation = field.getAnnotation(XDiamondValue.class);
                    Method keyMethod = annotation.getClass().getMethod("key", null);
                    String key = (String) keyMethod.invoke(annotation, null);
                    if (XDiamondValueInit.map.size() > 0) {
                        // 赋值
                        field.setAccessible(true);
                        String xDiamondValue = XDiamondValueInit.map.get(key);
                        if (StringUtils.isEmpty(xDiamondValue)) {
                            logger.info("get key {} from XDiamond is null", key);
                            Method defaultValueMethod = annotation.getClass().getDeclaredMethod("defaultValue", null);
                            String defaultValue = (String) defaultValueMethod.invoke(annotation, null);
                            Object value = ConvertUtils.convert(defaultValue, field.getType());
                            field.set(obj, value);
                        } else {
                            Object value = ConvertUtils.convert(xDiamondValue, field.getType());
                            field.set(obj, value);
                        }
                    }
                    if (!isPrototype) {
                        List<FieldEvent> beanList = oneKeyListenerMap.get(key);
                        if (beanList == null) {
                            beanList = new CopyOnWriteArrayList<FieldEvent>();
                        }
                        FieldEvent fieldEvent = new FieldEvent();
                        fieldEvent.setBean(obj);
                        fieldEvent.setField(field);
                        beanList.add(fieldEvent);
                        oneKeyListenerMap.put(key, beanList);
                    }
                } catch (Exception e) {
                    logger.error("get annotation field failed in class {}", bean.getClass().getName(), e);
                }
            }
        }
        return bean;
    }


    private static Object getCglibProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
        h.setAccessible(true);
        Object dynamicAdvisedInterceptor = h.get(proxy);

        Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
        advised.setAccessible(true);

        Object target = ((AdvisedSupport) advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();

        return target;
    }


    private static Object getJdkDynamicProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        AopProxy aopProxy = (AopProxy) h.get(proxy);

        Field advised = aopProxy.getClass().getDeclaredField("advised");
        advised.setAccessible(true);

        Object target = ((AdvisedSupport) advised.get(aopProxy)).getTargetSource().getTarget();

        return target;
    }



}
