package com.project.journalApp.controllers;

import com.project.journalApp.entity.User;
import com.project.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUser(){
        return userService.getAll();
    }

    @PostMapping
    public void addUser(@RequestBody User user){
        userService.save(user);
    }

    @PutMapping("/{userName}")
    public void updateUser(@RequestBody User user,@PathVariable String userName){
        User userDB = userService.getByUserName(userName);
        if(userDB != null){
            userDB.setUserName(user.getUserName());
            userDB.setPassword(user.getPassword());
            userService.save(userDB);
        }
    }
}
