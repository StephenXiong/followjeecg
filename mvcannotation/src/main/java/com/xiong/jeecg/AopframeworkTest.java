package com.xiong.jeecg;

import java.io.InputStream;

/**
 * @author xiongyufeng
 * @version 2018/6/26
 */
public class AopframeworkTest {
    public static void main(String[] args) {
        InputStream ips = AopframeworkTest.class.getClassLoader().getResourceAsStream("config.properties");
        Object bean = new BeanFactory(ips).getBean("xxx");
        System.out.println(bean.getClass().getName());
    }
}
