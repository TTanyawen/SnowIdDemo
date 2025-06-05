package com.example.snowiddemo.controller;

import com.example.snowiddemo.dao.entity.User;
import com.example.snowiddemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/save")
    public String saveUser() {
        User user=new User();
        user.setName("张三");
        user.setAge(18);
        User user2=userService.saveUser(user);
        return user2.getId().toString();
    }
}
