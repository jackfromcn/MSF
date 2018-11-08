package com.util.msf.monitor.annotation;

import com.util.msf.monitor.enums.MetricsMonitorEnum;

import java.lang.annotation.*;

/**
 * @author wencheng
 * @ClassName MetricsMonitor
 * @Description 数据采集注解
 * @date 2017-11-02 下午11:58
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface MetricsMonitor {
    /**
     * 数据采集类型
     *
     * @return 类型
     */
    MetricsMonitorEnum type();


    /**
     * url
     *
     * @return url
     */
    String url() default "";


    /**
     * 描述
     *
     * @return 描述
     */
    String desc() default "外部系统通信";

    /**
     * topic
     *
     * @return topic
     */
    String topic() default "";

}
