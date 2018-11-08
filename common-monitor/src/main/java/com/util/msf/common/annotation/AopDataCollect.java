package com.util.msf.common.annotation;

import com.util.msf.common.enumns.DataCollectorTypeEnum;

import java.lang.annotation.*;

/**
 * @author wencheng
 * @ClassName AopDataCollect
 * @Description 数据采集注解
 * @date 2017-11-02 下午11:58
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface AopDataCollect {
    /**
     * 数据采集类型
     *
     * @return 类型
     */
    DataCollectorTypeEnum type();

    /**
     * 响应码
     *
     * @return 响应吗
     */
    String code() default "";

    /**
     * url
     *
     * @return url
     */
    String url() default "";

    /**
     * kafka topic
     *
     * @return topic
     */
    String topic() default "";

    /**
     * 标识
     *
     * @return 标识
     */
    String identifyKey() default "";
}