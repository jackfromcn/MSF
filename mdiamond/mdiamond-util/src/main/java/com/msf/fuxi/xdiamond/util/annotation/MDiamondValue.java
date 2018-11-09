package com.msf.fuxi.xdiamond.util.annotation;

import java.lang.annotation.*;

/**
 * @author liujianchao
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MDiamondValue {

    String key();

    String defaultValue() default "";
}
