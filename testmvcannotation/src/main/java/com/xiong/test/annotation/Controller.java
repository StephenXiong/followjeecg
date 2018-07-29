package com.xiong.test.annotation;

import java.lang.annotation.*;

/**
 * @author xiongyufeng
 * @version 2018/7/4
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
    String value() default "";
}
