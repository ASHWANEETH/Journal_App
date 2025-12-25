package com.project.journalApp.controllers;

import com.project.journalApp.entity.User;
import com.project.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping
    public void updateUser(@RequestBody User user){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User userDB = userService.getByUserName(userName);
        userDB.setUserName(user.getUserName());
        userDB.setPassword(user.getPassword());
        userService.saveNewUser(userDB);
    }

    @DeleteMapping
    public void deleteUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userService.deleteByUserName(auth.getName());
    }
}
