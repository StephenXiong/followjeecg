package com.xiong.test.annotation;

import java.lang.annotation.*;

/**
 * @author xiongyufeng
 * @version 2018/7/4
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Service {
    String value() default "";
}
