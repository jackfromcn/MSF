package com.util.msf.log.config;

import com.util.msf.log.aop.TraceAspect;
import com.util.msf.log.interceptor.TraceInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Auther: wencheng
 * @Date: 2018/6/25 18:17
 * @Description:
 */
public class TraceAutoConfiguration extends WebMvcConfigurerAdapter {


    private static final Logger logger = LoggerFactory.getLogger(org.springframework.cloud.sleuth.autoconfig.TraceAutoConfiguration.class);

    @Autowired
    private Tracer tracer;

    @Bean
    @ConditionalOnMissingBean(TraceInterceptor.class)
    public TraceInterceptor traceInterceptor() {
        logger.info("日志链路追踪——拦截器初始化完成");
        return new TraceInterceptor(tracer);
    }

    @Bean
    @ConditionalOnMissingBean(TraceAspect.class)
    public TraceAspect traceAspect() {
        logger.info("日志链路追踪——切面初始化完成");
        return new TraceAspect(tracer);
    }

    @Bean
    @ConditionalOnClass(ApplicationContextAware.class)
    public ApplicationContextSupport applicationContextSupport() {
        return new ApplicationContextSupport();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(traceInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
        logger.info("日志链路追踪——拦截器配置完成,pathPattern:/**");
    }

}
