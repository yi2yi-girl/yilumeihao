package com.springxiaobudian.spring.anotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by yiye on 2018/6/26.
 */
@Target(ElementType.TYPE)
@Retention(RUNTIME)
@Documented
public @interface  Controller {
    String value() default "";
}
