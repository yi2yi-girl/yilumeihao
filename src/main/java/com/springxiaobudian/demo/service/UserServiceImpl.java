package com.springxiaobudian.demo.service;


import com.springxiaobudian.spring.anotation.Service;

/**
 * Created by yiye on 2018/6/26.
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public String getName(String name) {
        return "my name is:" + name;
    }
}
