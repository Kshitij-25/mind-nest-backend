package com.kshitijcodecraft.mind_nest.service;

import com.kshitijcodecraft.mind_nest.entity.User;
import com.kshitijcodecraft.mind_nest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public boolean isEmailRegistered(String email) {
        return userRepository.existsByEmail(email);
    }

    public List<User> getAllProfessionals() {
        return userRepository.findByRole("PROFESSIONAL");
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }
}