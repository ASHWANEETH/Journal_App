package com.project.journalApp.service;

import com.project.journalApp.Repository.UserRepository;
import com.project.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveNew(User userEntry){
        userEntry.setPassword(passwordEncoder.encode(userEntry.getPassword()));
        userEntry.setUserRoles(Arrays.asList("USER"));
        userRepository.save(userEntry);
    }

    public void update(User userEntry){
        userRepository.save(userEntry);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User getByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    public void deleteByUserName(String userName){
        userRepository.deleteByUserName(userName);
    }

}
