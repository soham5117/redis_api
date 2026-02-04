package com.api.redis.controller;

import com.api.redis.dao.UserDao;
import com.api.redis.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private UserDao userDao;

    @PostMapping
    public User createUser (@RequestBody User user){
        user.setId(UUID.randomUUID().toString());
        return userDao.save(user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable String id){
        return userDao.get(id);
    }

    @GetMapping
    public Map<Object, Object> findAll(){
        return userDao.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        userDao.delete(id);
    }

}
