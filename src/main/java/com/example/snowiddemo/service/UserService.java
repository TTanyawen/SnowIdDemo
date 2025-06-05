package com.example.snowiddemo.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.snowiddemo.dao.entity.User;
import com.example.snowiddemo.dao.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    public User saveUser(User user) {
        this.save(user);
        return user;
    }
}