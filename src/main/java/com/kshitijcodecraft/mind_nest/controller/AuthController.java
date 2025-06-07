package com.kshitijcodecraft.mind_nest.controller;

import com.kshitijcodecraft.mind_nest.dto.*;
import com.kshitijcodecraft.mind_nest.entity.User;
import com.kshitijcodecraft.mind_nest.entity.UserProfile;
import com.kshitijcodecraft.mind_nest.exception.CustomException;
import com.kshitijcodecraft.mind_nest.repository.UserRepository;
import com.kshitijcodecraft.mind_nest.security.CustomUserDetails;
import com.kshitijcodecraft.mind_nest.security.JwtUtils;
import com.kshitijcodecraft.mind_nest.service.ProfessionalService;
import com.kshitijcodecraft.mind_nest.service.ProfileService;
import com.kshitijcodecraft.mind_nest.service.TokenBlacklistService;
import com.kshitijcodecraft.mind_nest.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    private final ProfileService profileService;
    private final TokenBlacklistService tokenBlacklistService;
    private final ProfessionalService professionalService;

    @GetMapping("/check-email")
    public ResponseEntity<Map<String, Boolean>> checkEmail(@RequestParam String email) {
        boolean isRegistered = userService.isEmailRegistered(email);
        Map<String, Boolean> response = Map.of("isRegistered", isRegistered);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody SignupRequest signupRequest) {
        try{
            // 1. Check if email exists
            if (userRepository.existsByEmail(signupRequest.getEmail())) {
                return  ResponseEntity
                        .ok()
                        .body(new ApiResponse<>("Email is already in use!", HttpStatus.OK.value(), null));
            }

            // 2. Create new user
            User user = new User();
            user.setEmail(signupRequest.getEmail());
            user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
            user.setRole(signupRequest.getRole());
            userRepository.save(user);

            // 3. Generate JWT token
//            String jwt = jwtUtils.generateToken(user.getEmail());
            return  ResponseEntity
                    .ok()
                    .body(new ApiResponse<>("User successfully registered", HttpStatus.OK.value(), null));
        }catch (CustomException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = userDetails.getUser();

            // ✅ Determine profile based on role
            Object profile = null;
            try {
                if ("USER".equalsIgnoreCase(user.getRole())) {
                    profile = profileService.getProfileByUser(user);  // Fetch User Profile
                } else if ("PROFESSIONAL".equalsIgnoreCase(user.getRole())) {
                    profile = professionalService.getProfessionalProfileByUser(user);  // Fetch Professional Profile
                }
            } catch (CustomException e) {
                profile = null; // Profile not found
            }

            String accessToken = jwtUtils.generateToken(user.getEmail());
            String refreshToken = jwtUtils.generateRefreshToken(userDetails);

            user.setRefreshToken(refreshToken);
            userRepository.save(user);

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("accessToken", accessToken);
            responseData.put("refreshToken", user.getRefreshToken());
            responseData.put("userId", user.getId());
            responseData.put("email", user.getEmail());
            responseData.put("role", user.getRole());
            responseData.put("profile", profile);  // ✅ Return the correct profile

            return ResponseEntity.ok(
                    new ApiResponse<>(
                            "User successfully logged in",
                            HttpStatus.OK.value(),
                            responseData
                    )
            );
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<?>> logout(
            HttpServletRequest request,
            @AuthenticationPrincipal User user
    ) {
        String token = jwtUtils.parseJwt(request);

        if (token == null || !jwtUtils.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>("Invalid token", HttpStatus.BAD_REQUEST.value(), null));
        }

        tokenBlacklistService.blacklistToken(token);
        return ResponseEntity.ok(
                new ApiResponse<>("Logout successful", HttpStatus.OK.value(), null)
        );
    }


    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();

        // Validate refresh token
        if (!jwtUtils.validateToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }

        // Extract username and generate new tokens
        String email = jwtUtils.extractUsername(refreshToken);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException("User not found"));


        String newAccessToken = jwtUtils.generateToken(user.getEmail());
        String newRefreshToken = jwtUtils.generateRefreshToken(user);

        // Update user's refresh token in DB
        user.setRefreshToken(newRefreshToken);
        userRepository.save(user);

        return ResponseEntity.ok(new AuthResponse(
                newAccessToken,
                newRefreshToken,
                user.getId(),
                user.getEmail(),
                user.getRole()
        ));
    }
}