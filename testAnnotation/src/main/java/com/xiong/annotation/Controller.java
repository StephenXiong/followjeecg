package com.xiong.annotation;

import java.lang.annotation.*;

/**
 * controller注解
 * @author xiongyufeng
 * @version 2018/6/27
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Controller {
    String value() default "";
}
