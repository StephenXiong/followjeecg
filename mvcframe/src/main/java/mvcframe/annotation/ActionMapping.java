package mvcframe.annotation;

import java.lang.annotation.*;

/**
 * @author xiongyufeng
 * @version 2018/7/3
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ActionMapping {
    String reqUrl() default "";
}
