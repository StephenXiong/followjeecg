package com.xiong.annotation;

import java.lang.annotation.*;

/**
 * @author xiongyufeng
 * @version 2018/7/2
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
    String value() default "";
}
