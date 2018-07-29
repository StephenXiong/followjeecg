package com.xiong.controller;

import com.xiong.annotation.Autowired;
import com.xiong.annotation.Controller;
import com.xiong.annotation.RequestMapping;
import com.xiong.service.MyService;

/**
 * @author xiongyufeng
 * @version 2018/7/2
 */
@Controller("myController")
public class MyController {
    @Autowired("myServiceImpl")
    private MyService myService;

    @RequestMapping("insert")
    public String insert(){
        myService.insert();
        return null;
    }


    @RequestMapping("update")
    public String update(){
        myService.update();
        return null;
    }


    @RequestMapping("delete")
    public String delete(){
        myService.delete();
        return null;
    }


    @RequestMapping("query")
    public String query(){
        myService.query();
        return null;
    }
}
