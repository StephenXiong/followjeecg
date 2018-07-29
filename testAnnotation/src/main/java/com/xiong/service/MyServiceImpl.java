package com.xiong.service;

import com.xiong.annotation.Service;

import java.util.Map;

/**
 * @author xiongyufeng
 * @version 2018/7/2
 */
@Service("myServiceImpl")
public class MyServiceImpl implements MyService{
    @Override
    public int insert() {
        System.out.println("insert sth");
        return 0;
    }

    @Override
    public int update() {
        System.out.println("update sth");
        return 0;
    }

    @Override
    public int delete() {
        System.out.println("delete sth");
        return 0;
    }

    @Override
    public int query() {
        System.out.println("query sth");
        return 0;
    }
}
