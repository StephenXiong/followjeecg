package com.xiong.test.controller;

import com.xiong.test.annotation.Autowired;
import com.xiong.test.annotation.Controller;
import com.xiong.test.annotation.RequestMapping;
import com.xiong.test.annotation.RequestParam;
import com.xiong.test.service.TestMyService;

/**
 * @author xiongyufeng
 * @version 2018/7/4
 */
@Controller("testMyController")
public class TestMyController {

    @Autowired("testMyService")
    private TestMyService testMyService;


    @RequestMapping("insert")
    public String insert() {
        testMyService.insert();
        return "run insert";
    }

    @RequestMapping("update")
    public String update(@RequestParam("userId") String userId, @RequestParam("age") int age, @RequestParam("sex") String sex) {
        testMyService.update();
        return String.format("hello, i am %s, i am %d, yeah, i am %s", userId, age, sex);
    }
}
