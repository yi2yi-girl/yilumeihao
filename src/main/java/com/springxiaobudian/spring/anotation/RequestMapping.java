package com.springxiaobudian.spring.anotation;

import java.lang.annotation.*;

/**
 * Created by yiye on 2018/6/26.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
    String value() default "";
}
