package com.xiong.jeecg;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * @author xiongyufeng
 * @version 2018/6/26
 */
public class ProxyTest {

    public static void main(String[] args) throws Exception {
        Class clazzProxy1 = Proxy.getProxyClass(Collection.class.getClassLoader(), Collection.class);
        System.out.println(clazzProxy1.getName());
        Constructor[] constructors = clazzProxy1.getConstructors();
        for (Constructor constructor : constructors) {
            printSth(constructor.getName(), constructor.getParameterTypes());
        }

        Method[] methods = clazzProxy1.getMethods();
        for (Method method : methods) {
            printSth(method.getName(), method.getParameterTypes());
        }

        Constructor constructor = clazzProxy1.getConstructor(InvocationHandler.class);
        Collection proxy1 = (Collection) constructor.newInstance(new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return null;
            }
        });
        System.out.println(proxy1.toString());
        proxy1.clear();
        Collection proxy2 = (Collection) getProxy(new HashSet(),new MyAdvice());
        proxy2.add(1);
        proxy2.add(2);
        proxy2.add(3);
        System.out.println(proxy2.size());

    }

    public static Object getProxy(final Object target,final Advice advice) {
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new MyInvocationHandler(target,advice)
        );
    }

    private static void printSth(String name, Class[] clazzParams) {
        StringBuilder sb = new StringBuilder(name);
        sb.append("(");
        for (Class clazzParam : clazzParams) {
            sb.append(clazzParam.getName() + ",");
        }
        if (clazzParams.length > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append(")");
        System.out.println(sb.toString());
    }

    static class MyInvocationHandler implements InvocationHandler {
        private Object target;
        private Advice advice;

        public MyInvocationHandler(Object target, Advice advice) {
            this.target = target;
            this.advice = advice;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            advice.beforeMethod(method);
            Object returnVal = method.invoke(target, args);
            advice.afterMethod(method);
            return returnVal;
        }
    }
}
