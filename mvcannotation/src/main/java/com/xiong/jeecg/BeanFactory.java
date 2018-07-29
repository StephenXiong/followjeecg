package com.xiong.jeecg;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author xiongyufeng
 * @version 2018/6/26
 */
public class BeanFactory {

    Properties properties = new Properties();
    public BeanFactory(InputStream ips) {
        try {
            properties.load(ips);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                ips.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Object getBean(String name){
        String className = properties.getProperty(name);
        try {
            Class clazz = Class.forName(className);
            Object bean = clazz.newInstance();
            if(bean instanceof ProxyBeanFactory){
                ProxyBeanFactory proxyBeanFactory = (ProxyBeanFactory) bean;
                Advice advice = (Advice) Class.forName(properties.getProperty(name+".advice")).newInstance();
                Object target = Class.forName(properties.getProperty(name+".target")).newInstance();
                proxyBeanFactory.setAdvice(advice);
                proxyBeanFactory.setTarget(target);
                Object proxy = proxyBeanFactory.getProxy();
                return proxy;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
