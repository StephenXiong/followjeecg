package com.xiong.annotation;

import java.lang.annotation.*;

/**
 *
 * @author xiongyufeng
 * @version 2018/6/27
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
    String value() default "";
}
