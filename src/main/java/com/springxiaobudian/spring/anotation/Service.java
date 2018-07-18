package com.springxiaobudian.spring.anotation;

import java.lang.annotation.*;

/**
 * Created by yiye on 2018/6/26.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Service {
    String value() default "";
}
