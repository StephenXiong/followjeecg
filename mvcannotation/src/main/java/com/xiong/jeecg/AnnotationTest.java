
package com.xiong.jeecg;

import java.lang.annotation.Annotation;

/**
 * @author xiongyufeng
 * @version 2018/6/20
 */
@MyAnnotation(color = "yellow",value = AnnotationEnum.TEST_ONE)
public class AnnotationTest {

    @MyAnnotation(AnnotationEnum.TEST_TWO)
    public static void main(String[] args) {
        if (AnnotationTest.class.isAnnotationPresent(MyAnnotation.class)) {
            MyAnnotation annotation = AnnotationTest.class.getAnnotation(MyAnnotation.class);
            System.out.println("hi，" + annotation.color());
        }
    }

    public static void sayHello() {
        System.out.println("Hi,xiongyufeng");
    }
}
