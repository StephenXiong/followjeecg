package com.xiong.jeecg;

import java.lang.reflect.Method;

/**
 * @author xiongyufeng
 * @version 2018/6/26
 */
public interface Advice {
    void beforeMethod(Method method);
    void afterMethod(Method method);
}
