package com.msf.fuxi.xdiamond.util;

import com.msf.fuxi.xdiamond.client.XDiamondConfig;
import com.msf.fuxi.xdiamond.client.annotation.AllKeyListener;
import com.msf.fuxi.xdiamond.client.annotation.OneKeyListener;
import com.msf.fuxi.xdiamond.client.event.ObjectListenerMethodInvokeWrapper;
import com.msf.fuxi.xdiamond.util.annotation.MDiamondValue;
import com.msf.fuxi.xdiamond.util.config.MDiamondConfigProperties;
import com.msf.fuxi.xdiamond.util.event.FieldWrapper;
import org.apache.commons.beanutils.ConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author lujianchao
 */
public class MDiamondConfigBeanPostProcessor implements InitializingBean, FactoryBean<XDiamondConfig>, Ordered, BeanPostProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MDiamondConfigBeanPostProcessor.class);

    private int order = 0;

    private MDiamondConfigProperties mDiamondConfigProperties;

    private XDiamondConfig xDiamondConfig;

    public MDiamondConfigBeanPostProcessor(MDiamondConfigProperties mDiamondConfigProperties) {
        this.mDiamondConfigProperties = mDiamondConfigProperties;
    }

    @Override
    public XDiamondConfig getObject() throws Exception {
        return this.xDiamondConfig;
    }

    @Override
    public Class<?> getObjectType() {
        return XDiamondConfig.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        xDiamondConfig = new XDiamondConfig();

        if (!StringUtils.isEmpty(mDiamondConfigProperties.getGroupId())) {
            xDiamondConfig.setGroupId(mDiamondConfigProperties.getGroupId());
        }

        if (!StringUtils.isEmpty(mDiamondConfigProperties.getArtifactId())) {
            xDiamondConfig.setArtifactId(mDiamondConfigProperties.getArtifactId());
        }

        if (!StringUtils.isEmpty(mDiamondConfigProperties.getProfile())) {
            xDiamondConfig.setProfile(mDiamondConfigProperties.getProfile());
        }

        if (!StringUtils.isEmpty(mDiamondConfigProperties.getVersion())) {
            xDiamondConfig.setVersion(mDiamondConfigProperties.getVersion());
        }

        if (mDiamondConfigProperties.getSecretKey() != null) {
            xDiamondConfig.setSecretKey(mDiamondConfigProperties.getSecretKey());
        }

        if (!StringUtils.isEmpty(mDiamondConfigProperties.getServerHost())) {
            xDiamondConfig.setServerHost(mDiamondConfigProperties.getServerHost());
        }

        if (mDiamondConfigProperties.getServerPort() != null) {
            xDiamondConfig.setServerPort(mDiamondConfigProperties.getServerPort());
        }

        if (mDiamondConfigProperties.getbPrintConfigWhenBoot() != null) {
            xDiamondConfig.setbPrintConfigWhenBoot(mDiamondConfigProperties.getbPrintConfigWhenBoot());
        }

//        if (mDiamondConfigProperties.getbSyncToSystemProperties() != null) {
//            xDiamondConfig.setbSyncToSystemProperties(mDiamondConfigProperties.getbSyncToSystemProperties());
//        }

        if (mDiamondConfigProperties.getbBackOffRetryInterval() != null) {
            xDiamondConfig.setbBackOffRetryInterval(mDiamondConfigProperties.getbBackOffRetryInterval());
        }

        if (mDiamondConfigProperties.getMaxRetryTimes() != null) {
            xDiamondConfig.setMaxRetryTimes(mDiamondConfigProperties.getMaxRetryTimes());
        }

        if (mDiamondConfigProperties.getRetryIntervalSeconds() != null) {
            xDiamondConfig.setRetryIntervalSeconds(mDiamondConfigProperties.getRetryIntervalSeconds());
        }

        if (mDiamondConfigProperties.getMaxRetryIntervalSeconds() != null) {
            xDiamondConfig.setMaxRetryIntervalSeconds(mDiamondConfigProperties.getMaxRetryIntervalSeconds());
        }

        xDiamondConfig.init();
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (mDiamondConfigProperties.isScanListener()) {
            Method[] methods = ReflectionUtils.getAllDeclaredMethods(bean.getClass());
            if (methods != null) {
                for (Method method : methods) {
                    OneKeyListener oneKeyListener = AnnotationUtils.findAnnotation(method, OneKeyListener.class);
                    if (oneKeyListener != null) {
                        ObjectListenerMethodInvokeWrapper wrapper = new ObjectListenerMethodInvokeWrapper();
                        wrapper.setxDiamondConfig(xDiamondConfig);
                        wrapper.setListenerClassName(OneKeyListener.class.getName());
                        wrapper.setKey(oneKeyListener.key());
                        wrapper.setTargetObject(bean);
                        wrapper.setTargetMethod(method.getName());
                        wrapper.init();
                    }

                    AllKeyListener allKeyListener = AnnotationUtils.findAnnotation(method, AllKeyListener.class);
                    if (allKeyListener != null) {
                        ObjectListenerMethodInvokeWrapper wrapper = new ObjectListenerMethodInvokeWrapper();
                        wrapper.setxDiamondConfig(xDiamondConfig);
                        wrapper.setListenerClassName(AllKeyListener.class.getName());
                        wrapper.setTargetObject(bean);
                        wrapper.setTargetMethod(method.getName());
                        wrapper.init();
                    }
                }
            }
        }

        if (mDiamondConfigProperties.isScanMdiamond()){

            Field[] fields = bean.getClass().getDeclaredFields();

            for (Field field : fields) {
                try {
                    if (field.isAnnotationPresent(MDiamondValue.class)) {
                        MDiamondValue mDiamondValueAnnotation = field.getAnnotation(MDiamondValue.class);
                        String key = mDiamondValueAnnotation.key();
                        String mDiamondValue = xDiamondConfig.getProperty(key);
                        field.setAccessible(true);
                        if (StringUtils.isEmpty(mDiamondValue)){
                            String defaultValue = mDiamondValueAnnotation.defaultValue();
                            field.set(bean, ConvertUtils.convert(defaultValue, field.getType()));
                        } else {
                            field.set(bean, ConvertUtils.convert(mDiamondValue, field.getType()));
                        }

                        FieldWrapper fieldWrapper = new FieldWrapper(bean, field, mDiamondValueAnnotation.key(), xDiamondConfig);
                        fieldWrapper.init();
                    }
                } catch (Exception e){
                    LOGGER.error("设置bean[{}]的field[{}] 异常，原因：",
                            bean.getClass().getName(), field.getName(), e);
                }
            }
        }

        return bean;
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

}
