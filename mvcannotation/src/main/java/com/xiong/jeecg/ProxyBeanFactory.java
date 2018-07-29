package com.xiong.jeecg;

/**
 * @author xiongyufeng
 * @version 2018/6/26
 */
public class ProxyBeanFactory {

    private Object target;

    private Advice advice;

    public Object getProxy() {
        return ProxyTest.getProxy(target,advice);
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Advice getAdvice() {
        return advice;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }
}
