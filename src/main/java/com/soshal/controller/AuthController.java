package com.soshal.controller;

import com.soshal.repository.UserRepo;
import com.soshal.request.LoginRequest;
import com.soshal.response.AutoResponse;
import com.soshal.modal.User;

import com.soshal.config.JwtProvider;
import com.soshal.service.SubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final SubscriptionService subscriptionService;

    // ✅ Constructor-based injection
    public AuthController(UserRepo userRepository, PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager, JwtProvider jwtProvider,
                          SubscriptionService subscriptionService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.subscriptionService = subscriptionService;
    }

    // ✅ Fixed: User Registration (Prevents NullPointerException)
    @PostMapping("/sign")
    public ResponseEntity<User> createUserHandler(@RequestBody User user) throws Exception {
        if (user.getEmail() == null || user.getPassword() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        // Prevents NullPointerException
        Optional<Optional<User>> existingUser = Optional.ofNullable(userRepository.findByEmail(user.getEmail()));
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User saved = userRepository.save(user);
        subscriptionService.createSubscription(saved.getId());
        return ResponseEntity.ok(saved);
    }

    // ✅ Fixed: Improved Login Authentication Handling
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            String token = jwtProvider.generateToken(authentication);
            return ResponseEntity.ok(new AutoResponse(token, "Login successful"));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new AutoResponse(null, "Invalid email or password"));
        }
    }
}
