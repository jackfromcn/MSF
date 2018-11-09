package com.msf.fuxi.xdiamond.client.annotation;

import java.lang.annotation.*;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AllKeyListener {

	/**
	 * XDiamond Config key.
	 * 
	 * @return
	 */
	String key() default "";

}