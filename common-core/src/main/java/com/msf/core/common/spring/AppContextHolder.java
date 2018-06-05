package com.msf.core.common.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Auther: wencheng
 * @Date: 2018/6/5 14:31
 * @Description:
 *
 * spring上下文的持有类
 */
@Component
public class AppContextHolder implements ApplicationContextAware{

    private static Logger logger = LoggerFactory.getLogger(AppContextHolder.class);
    private static ApplicationContext applicationContext;

    public AppContextHolder() {
        logger.info("初始化ContextHolder..............................");
    }

    @Override
    public void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
        logger.info("设置ApplicationContext...........................");
    }

    public static void load(ApplicationContext context) {
        applicationContext = context;
        logger.info("设置ApplicationContext...........................");
    }

    /**
     * 获取spring上下文 ApplicationContext
     *
     * @return
     */
    public static ApplicationContext applicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException("applicaitonContext未注入,请在spring配置文件中定义AppContextHolder");
        }
        return applicationContext;
    }

    /**
     * 根据名称获取spring管理的bean
     *
     * @param name
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }

    /**
     * 根据类型获取spring管理的bean
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }
}
