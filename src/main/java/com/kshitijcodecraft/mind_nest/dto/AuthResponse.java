package com.kshitijcodecraft.mind_nest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String accessToken;
    private String refreshToken; // Added
    private Long userId;
    private String email;
    private String role;
}