package com.kshitijcodecraft.mind_nest.service;

import com.kshitijcodecraft.mind_nest.dto.UserProfileRequest;
import com.kshitijcodecraft.mind_nest.entity.UserProfile;
import com.kshitijcodecraft.mind_nest.repository.UserProfileRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
@Transactional
@RequiredArgsConstructor
public class UserProfileService {
    private final UserProfileRepository userProfileRepository;

    public UserProfileRequest createProfile(UserProfileRequest profileDTO) {
        validateProfile(profileDTO);

        UserProfile profile = profileDTO.toEntity();
        profile.setEmail(profile.getEmail().toLowerCase());

        UserProfile savedProfile = userProfileRepository.save(profile);
        return savedProfile.toDTO();
    }

    private void validateProfile(UserProfileRequest profileDTO) {
        if (!profileDTO.isConsentGiven()) {
            throw new ValidationException("Consent must be given to create a profile");
        }

        if (profileDTO.getDateOfBirth() != null) {
            int age = Period.between(profileDTO.getDateOfBirth(), LocalDate.now()).getYears();
            if (age < 18) {
                throw new ValidationException("Must be 18 or older to create a profile");
            }
        }

        if (profileDTO.getEmergencyContactName() != null &&
                (profileDTO.getEmergencyContactPhone() == null ||
                        profileDTO.getEmergencyContactRelationship() == null)) {
            throw new ValidationException("Emergency contact details must be complete if provided");
        }
    }
}