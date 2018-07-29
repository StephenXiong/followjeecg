package com.xiong.test.annotation;

import java.lang.annotation.*;

/**
 * @author xiongyufeng
 * @version 2018/7/5
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestParam {
    String value() default "";
}
