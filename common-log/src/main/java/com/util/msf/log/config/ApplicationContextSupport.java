package com.util.msf.log.config;

import org.springframework.beans.BeansException;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @Auther: wencheng
 * @Date: 2018/6/25 18:17
 * @Description:
 */
public class ApplicationContextSupport implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static class TraceInnerClass {
        public static Tracer tracer = applicationContext == null ? null : applicationContext.getBean(Tracer.class);
    }

    @SuppressWarnings("static-access")
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (applicationContext != null) {
            ApplicationContextSupport.applicationContext = applicationContext;
        }

    }

    /**
     * 获取bean
     *
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> cls) {
        return applicationContext == null ? null : applicationContext.getBean(cls);
    }

    /**
     * 获取bean
     *
     * @param name
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return applicationContext == null ? null : (T) applicationContext.getBean(name);
    }
}
