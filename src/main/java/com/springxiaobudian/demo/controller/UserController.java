package com.springxiaobudian.demo.controller;

import com.springxiaobudian.demo.service.UserService;
import com.springxiaobudian.spring.anotation.Autowired;
import com.springxiaobudian.spring.anotation.Controller;
import com.springxiaobudian.spring.anotation.RequestMapping;
import com.springxiaobudian.spring.anotation.RequestParam;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yiye on 2018/6/26.
 */


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/query.json")
    public void query(HttpServletRequest req, HttpServletResponse resp,@RequestParam("name") String name) {
        String result = userService.getName(name);
        System.out.println(result);
    }
}
