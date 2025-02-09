package com.kshitijcodecraft.mind_nest.service;

import java.time.LocalDate;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.kshitijcodecraft.mind_nest.dto.ProfileRequest;
import com.kshitijcodecraft.mind_nest.entity.User;
import com.kshitijcodecraft.mind_nest.entity.UserProfile;
import com.kshitijcodecraft.mind_nest.exception.CustomException;
import com.kshitijcodecraft.mind_nest.repository.ProfileRepository;
import com.kshitijcodecraft.mind_nest.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserProfile saveProfile(User user, ProfileRequest request) {
        // Find existing profile or create a new one
        UserProfile profile = profileRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    UserProfile newProfile = new UserProfile();
                    newProfile.setUser(user);
                    return newProfile;
                });

        modelMapper.map(request, profile);

        // Handle embedded objects
        profile.setEmergencyContact(request.getEmergencyContact());
        profile.setTherapyPreferences(request.getTherapyPreferences());

        // Age validation
        if (profile.getDateOfBirth() != null) {
            LocalDate eighteenYearsAgo = LocalDate.now().minusYears(18);
            if (profile.getDateOfBirth().isAfter(eighteenYearsAgo)) {
                throw new CustomException("Users must be 18 years or older");
            }
        }

        // Save the profile (JPA will update if it exists)
        return profileRepository.save(profile);
    }

    public UserProfile getProfileByUser(User user) throws CustomException {
        System.out.println("Fetching profile for user ID: " + user.getId());
        return profileRepository.findByUser(user)
                .orElseThrow(() -> new CustomException("Profile not found for user"));
    }
}