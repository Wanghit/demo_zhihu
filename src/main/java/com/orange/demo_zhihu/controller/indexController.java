package com.orange.demo_zhihu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017/6/2.
 * 主页
 */
@Controller
public class indexController {
    @RequestMapping(path = {"/index", "/"})
    @ResponseBody
    public String index() {
        return "Hello demo_zhihu";
    }
    @RequestMapping("/profile/{userid}")
    @ResponseBody
    public String profile(@PathVariable("userid") int userid) {
        return String.format("Profile Page of %d:",userid);
    }

}
