package com.example.user_service.controller;

import com.example.user_service.model.User;
import com.example.user_service.service.UserService;
import com.example.user_service.security.JwtUtil;
import com.example.user_service.dto.AuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        if (userService.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already registered");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(User.Role.valueOf(request.getRole()));
        userService.saveUser(user);

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            return userService.findByEmail(request.getEmail())
                    .map(user -> {
                        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                            String token = jwtUtil.generateToken(user);
                            return ResponseEntity.ok(Map.of("token", token));
                        } else {
                            return ResponseEntity.status(401).body("Invalid credentials");
                        }
                    })
                    .orElse(ResponseEntity.status(404).body("User not found"));
        } catch (Exception e) {
            e.printStackTrace(); // <-- This will print the real error
            return ResponseEntity.status(500).body("Server error: " + e.getMessage());
        }
    }
}
