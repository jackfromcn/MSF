package com.util.msf.log.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Auther: wencheng
 * @Date: 2018/6/25 18:05
 * @Description: 日志链路追踪注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MTrace {

    /**
     * 业务主属性
     */
    String value();
}
