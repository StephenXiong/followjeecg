package com.xiong.test.service;

import com.xiong.test.annotation.Service;

/**
 * @author xiongyufeng
 * @version 2018/7/4
 */
@Service("testMyService")
public class TestMyServiceImpl implements TestMyService{
    @Override
    public void insert() {
        System.out.println("hello world!I am insert");
    }

    @Override
    public void update() {
        System.out.println("hello world!I am update");
    }
}
