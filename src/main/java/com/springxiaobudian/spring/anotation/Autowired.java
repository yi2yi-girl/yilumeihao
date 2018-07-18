package com.springxiaobudian.spring.anotation;

import java.lang.annotation.*;

/**
 * Created by yiye on 2018/7/15.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
    String value() default "";
}
