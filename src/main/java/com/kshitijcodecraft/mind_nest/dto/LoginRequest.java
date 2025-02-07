package com.kshitijcodecraft.mind_nest.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}