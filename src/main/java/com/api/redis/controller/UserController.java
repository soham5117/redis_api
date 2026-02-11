package com.api.redis.controller;

import com.api.redis.dao.LoginRequest;
import com.api.redis.dao.UserDao;
import com.api.redis.entity.User;
import com.api.redis.service.AuthService;
import com.api.redis.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    // LOGIN
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request, HttpSession session) {

        User user = authService.authenticate(request);

        session.setAttribute("USER_ID", user.getId());
        session.setAttribute("EMAIL", user.getEmail());
        session.setAttribute("ROLE", user.getRole());

        return "Login successful";
    }

    // LOGOUT
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "Logged out successfully";
    }

    // CURRENT USER (for frontend role & profile)
    @GetMapping("/me")
    public Map<String, Object> me(HttpSession session) {

        if (session.getAttribute("USER_ID") == null) {
            throw new RuntimeException("Unauthorized");
        }

        Map<String, Object> data = new HashMap<>();
        data.put("email", session.getAttribute("EMAIL"));
        data.put("role", session.getAttribute("ROLE"));

        return data;
    }

    @PostMapping("/add")
    public User createUser (@RequestBody User user){
        user.setId(UUID.randomUUID().toString());
        return userService.createUser(user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable String id){
        return userService.getUserById(id);
    }

    @GetMapping("/get/all")
    public Map<Object, Object> getAllUsers(HttpSession session) {

        if (session.getAttribute("USER_ID") == null) {
            throw new RuntimeException("Unauthorized");
        }

        return userService.getAllUsers();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id , HttpSession session){
        if (session.getAttribute("USER_ID") == null) {
            throw new RuntimeException("Unauthorized");
        }
        userService.deleteUser(id);
    }


    @GetMapping("/profile")
    public User getProfile(HttpSession session) {

        String userId = (String) session.getAttribute("USER_ID");

        if (userId == null) {
            throw new RuntimeException("Session expired or not logged in");
        }

        return userService.getUserById(userId);
    }

    @GetMapping("/secure")
    public String secureApi(HttpSession session) {

        if (session.getAttribute("USER_ID") == null) {
            return "Unauthorized";
        }

        return "Authorized user";
    }


}
