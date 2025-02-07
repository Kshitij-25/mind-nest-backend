package com.kshitijcodecraft.mind_nest.service;

import com.kshitijcodecraft.mind_nest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public boolean isEmailRegistered(String email) {
        return userRepository.existsByEmail(email);
    }
}