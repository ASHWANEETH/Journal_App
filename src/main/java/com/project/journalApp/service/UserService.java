package com.project.journalApp.service;

import com.project.journalApp.Repository.UserRepository;
import com.project.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void save(User userEntry){
        userRepository.save(userEntry);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User getByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    public void delete(ObjectId id){
        userRepository.deleteById(id);
    }

}
