package com.msf.fuxi.xdiamond.util;

import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.context.annotation.Scope;

import java.lang.reflect.Field;

/**
 * @author liujianchao
 */
public class BeanUtils {

    public static Object getRealObject(Object bean) throws Exception {
        // 获取注解的字段
        if (AopUtils.isAopProxy(bean)) {
            // 如果为代理，获取代理的真实对象
            if (AopUtils.isJdkDynamicProxy(bean)) {
                bean = getJdkDynamicProxyTargetObject(bean);
            } else {
                bean = getCglibProxyTargetObject(bean);
            }

//            try {
//
//            } catch (Exception e) {
////                    logger.info("Failed to get proxy object {}", bean.getClass().getName(), e);
//            }
        }
        return bean;
    }


    public static boolean isPrototype(Object bean){
        AnnotatedGenericBeanDefinition beanDefinition = new AnnotatedGenericBeanDefinition(bean.getClass());
        if (beanDefinition.getMetadata().isAnnotated(Scope.class.getName())) {
            String value = (String) beanDefinition.getMetadata().getAnnotationAttributes(Scope.class.getName()).get("value");
            if (value.equals("prototype")) {
                return true;
            }
        }

        return false;
    }

    public static Object getCglibProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
        h.setAccessible(true);
        Object dynamicAdvisedInterceptor = h.get(proxy);

        Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
        advised.setAccessible(true);

        Object target = ((AdvisedSupport) advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();

        return target;
    }


    public static Object getJdkDynamicProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        AopProxy aopProxy = (AopProxy) h.get(proxy);

        Field advised = aopProxy.getClass().getDeclaredField("advised");
        advised.setAccessible(true);

        Object target = ((AdvisedSupport) advised.get(aopProxy)).getTargetSource().getTarget();

        return target;
    }
}
