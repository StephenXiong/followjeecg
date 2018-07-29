package com.xiong.test.annotation;

import java.lang.annotation.*;

/**
 * @author xiongyufeng
 * @version 2018/7/4
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
    String value() default "";
}
