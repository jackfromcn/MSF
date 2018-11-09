package com.msf.fuxi.xdiamond;

import com.msf.fuxi.xdiamond.event.BeanFieldEvent;
import com.msf.fuxi.xdiamond.init.XDiamondValueInit;
import com.msf.fuxi.xdiamond.listener.XDiamondListenerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;


public class XDiamondUtilConfigure implements BeanDefinitionRegistryPostProcessor {

    Logger logger = LoggerFactory.getLogger(XDiamondUtilConfigure.class);


    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }


    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        String beanName = "beanFieldEvent";
        BeanDefinition beanDefinition = createBeanFieldEvent();
        registry.registerBeanDefinition(beanName, beanDefinition);
        logger.info("Regist {} bean to spring context.", beanName);
        beanName = "xDiamondValueInit";
        beanDefinition = createXDiamondValueInit();
        registry.registerBeanDefinition(beanName, beanDefinition);
        this.logger.info("Regist {} bean to spring context", beanName);
        beanName = "xDiamondListenerService";
        beanDefinition = createXDiamondListenerService();
        registry.registerBeanDefinition(beanName, beanDefinition);
        this.logger.info("Regist {} bean to spring context", beanName);
    }


    private BeanDefinition createBeanFieldEvent() {
        Class beanClass = BeanFieldEvent.class;
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(beanClass);
        return builder.getRawBeanDefinition();
    }


    private BeanDefinition createXDiamondValueInit() {
        Class beanClass = XDiamondValueInit.class;
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(beanClass);
        return builder.getRawBeanDefinition();
    }


    private BeanDefinition createXDiamondListenerService() {
        Class beanClass = XDiamondListenerService.class;
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(beanClass);
        return builder.getRawBeanDefinition();
    }

}
