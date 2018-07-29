package com.xiong.webserver;

/**
 * @author xiongyufeng
 * @version 2018/6/19
 */
public class MyClassLoader extends ClassLoader{

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return super.loadClass(name);
    }
}
