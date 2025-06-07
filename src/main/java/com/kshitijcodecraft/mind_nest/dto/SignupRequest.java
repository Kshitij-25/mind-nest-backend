package com.kshitijcodecraft.mind_nest.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private String email;
    private String password;
    private String role;
}