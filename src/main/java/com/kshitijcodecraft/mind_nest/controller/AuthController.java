package com.kshitijcodecraft.mind_nest.controller;

import com.kshitijcodecraft.mind_nest.dto.AuthResponse;
import com.kshitijcodecraft.mind_nest.dto.LoginRequest;
import com.kshitijcodecraft.mind_nest.dto.SignupRequest;
import com.kshitijcodecraft.mind_nest.entity.User;
import com.kshitijcodecraft.mind_nest.repository.UserRepository;
import com.kshitijcodecraft.mind_nest.security.CustomUserDetails;
import com.kshitijcodecraft.mind_nest.security.JwtUtils;
import com.kshitijcodecraft.mind_nest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @GetMapping("/check-email")
    public ResponseEntity<Map<String, Boolean>> checkEmail(@RequestParam String email) {
        boolean isRegistered = userService.isEmailRegistered(email);
        Map<String, Boolean> response = Map.of("isRegistered", isRegistered);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        // 1. Check if email exists
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }

        // 2. Create new user
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setRole(signupRequest.getRole());
        user.setFirstName(signupRequest.getFirstName());
        user.setLastName(signupRequest.getLastName());

        userRepository.save(user);

        // 3. Generate JWT token
        String jwt = jwtUtils.generateToken(user.getEmail());
        return ResponseEntity.ok(
                new AuthResponse(
                        jwt,
                        user.getId(),
                        user.getEmail(),
                        user.getRole()
                )
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // ✅ Get CustomUserDetails instead of casting
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();

        String jwt = jwtUtils.generateToken(user.getEmail());
        return ResponseEntity.ok(
                new AuthResponse(
                        jwt,
                        user.getId(),
                        user.getEmail(),
                        user.getRole()
                )
        );
    }
}