package com.msf.fuxi.xdiamond.annotation;

import java.lang.annotation.*;

@Deprecated
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface XDiamondValue {

    public abstract String key() default "";


    public abstract String defaultValue() default "";
}
