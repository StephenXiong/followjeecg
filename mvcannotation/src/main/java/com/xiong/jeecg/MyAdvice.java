package com.xiong.jeecg;

import java.lang.reflect.Method;

/**
 * @author xiongyufeng
 * @version 2018/6/26
 */
public class MyAdvice implements Advice {
    long beginTime = 0;

    public void beforeMethod(Method method) {
        System.out.println("exec before");
        beginTime = System.currentTimeMillis();
    }

    public void afterMethod(Method method) {
        System.out.println("exec after");
        long endTime = System.currentTimeMillis();
        System.out.println(method.getName() +" running takes "+ (endTime-beginTime)+"ms");
    }
}
