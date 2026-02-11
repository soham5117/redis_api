package com.api.redis.service;

import com.api.redis.entity.User;
import com.api.redis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        user.setId(UUID.randomUUID().toString());
        userRepository.save(user);
        return user;
    }

    public User getUserById(String id) {
        return userRepository.get(id);
    }

    public Map<Object, Object> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
