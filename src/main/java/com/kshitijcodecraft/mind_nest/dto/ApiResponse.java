package com.kshitijcodecraft.mind_nest.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

// ApiResponse.java
@Data
@Builder
public class ApiResponse<T> {
    private int status;
    private String message;
    private T data;
    private List<String> errors;
}