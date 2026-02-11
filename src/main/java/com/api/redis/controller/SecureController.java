package com.api.redis.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SecureController {

    @GetMapping("/profile")
    public String profile(HttpSession session) {

        if (session.getAttribute("USER_ID") == null) {
            throw new RuntimeException("Unauthorized");
        }

        return "This is a protected profile API";
    }

    @GetMapping("/admin")
    public String admin(HttpSession session) {

        String role = (String) session.getAttribute("ROLE");

        if (!"ADMIN".equals(role)) {
            throw new RuntimeException("Access denied");
        }

        return "Admin API accessed";
    }
}
