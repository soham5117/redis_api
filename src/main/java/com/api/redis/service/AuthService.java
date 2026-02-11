package com.api.redis.service;

import com.api.redis.dao.LoginRequest;
import com.api.redis.entity.User;
import com.api.redis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public User authenticate(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail());

        if (user == null) {
            throw new RuntimeException("Invalid email or password");
        }

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return user;
    }
}
