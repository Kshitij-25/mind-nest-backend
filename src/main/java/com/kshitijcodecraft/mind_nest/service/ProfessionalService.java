package com.kshitijcodecraft.mind_nest.service;

import com.kshitijcodecraft.mind_nest.dto.professionals.*;
import com.kshitijcodecraft.mind_nest.dto.ProfessionalRegistrationDTO;
import com.kshitijcodecraft.mind_nest.embedded.*;
import com.kshitijcodecraft.mind_nest.entity.Assessment;
import com.kshitijcodecraft.mind_nest.entity.ProfessionalProfile;
import com.kshitijcodecraft.mind_nest.entity.User;
import com.kshitijcodecraft.mind_nest.exception.CustomException;
import com.kshitijcodecraft.mind_nest.repository.AssessmentRepository;
import com.kshitijcodecraft.mind_nest.repository.ProfessionalRepository;
import com.kshitijcodecraft.mind_nest.repository.UserRepository;
import jakarta.persistence.criteria.Join;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import jakarta.persistence.criteria.Predicate;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProfessionalService {
    private final ProfessionalRepository professionalRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
//    private final Assessment assessment;
    private final AssessmentRepository assessmentRepository;

    public ProfessionalProfile createProfessional(User user, ProfessionalRegistrationDTO dto) {
        ProfessionalProfile profile = new ProfessionalProfile();

        // Map basic fields
        profile.setUser(user);
        profile.setFullName(dto.getFullName());
        profile.setDateOfBirth(LocalDate.parse(dto.getDateOfBirth()));
        profile.setGenderIdentity(dto.getGenderIdentity());
        profile.setPreferredPronouns(dto.getPreferredPronouns());
        profile.setContactNumber(dto.getContactNumber());
//        profile.setProfilePicUrl(dto.getProfilePicUrl());
        profile.setEmail(dto.getEmail());
        profile.setAddress(dto.getAddress());

        // Map nested DTOs
        profile.setCredentials(mapCredentials(dto.getCredentials()));
        profile.setEducationHistory(mapEducation(dto.getEducationHistory()));
        profile.setExperience(mapExperience(dto.getExperience()));
        profile.setTherapeuticModalities(new HashSet<>(dto.getTherapeuticModalities()));
        profile.setAvailability(mapAvailability(dto.getAvailability()));
        profile.setPaymentInfo(mapPaymentInfo(dto.getPaymentInfo()));
        profile.setBackgroundCheckConsent(dto.getBackgroundCheckConsent());
        profile.setTermsAccepted(dto.getTermsAccepted());

        return professionalRepository.save(profile);
    }

    private ProfessionalCredentials mapCredentials(CredentialsDTO dto) {
        ProfessionalCredentials credentials = new ProfessionalCredentials();
        credentials.setProfessionalTitle(dto.getProfessionalTitle());
        credentials.setLicenseNumber(dto.getLicenseNumber());
        credentials.setIssuingAuthority(dto.getIssuingAuthority());
        credentials.setLicenseExpiry(LocalDate.parse(dto.getLicenseExpiry()));
        credentials.setMultipleLicenses(dto.getMultipleLicenses());
        credentials.setBoardCertified(dto.getBoardCertified());
        return credentials;
    }

    private List<Education> mapEducation(List<EducationDTO> educationDtos) {
        return educationDtos.stream().map(dto -> {
            Education education = new Education();
            education.setDegree(dto.getDegree());
            education.setInstitution(dto.getInstitution());
            education.setGraduationYear(dto.getGraduationYear());
            education.setSpecializationsList(dto.getSpecializations());
            return education;
        }).collect(Collectors.toList());
    }

    private ProfessionalExperience mapExperience(ExperienceDTO dto) {
        ProfessionalExperience experience = new ProfessionalExperience();
        experience.setYearsExperience(dto.getYearsExperience());
        experience.setExpertiseAreas(new HashSet<>(dto.getExpertiseAreas()));
        experience.setPreferredAgeGroups(new HashSet<>(dto.getPreferredAgeGroups()));
        experience.setLanguagesSpoken(new HashSet<>(dto.getLanguagesSpoken()));
        experience.setOffersCouplesTherapy(dto.getOffersCouplesTherapy());
        experience.setOffersGroupTherapy(dto.getOffersGroupTherapy());
        return experience;
    }

    private AvailabilitySchedule mapAvailability(AvailabilityDTO dto) {
        AvailabilitySchedule availability = new AvailabilitySchedule();
        availability.setDailyAvailability(convertAvailabilityToMap(dto.getDailyAvailability()));
        availability.setTimeZone(dto.getTimeZone());
        availability.setAcceptingNewClients(dto.getAcceptingNewClients());
        return availability;
    }

    private Map<String, String> convertAvailabilityToMap(DailyAvailabilityDTO dto) {
        Map<String, String> availabilityMap = new LinkedHashMap<>();
        if (dto.getMonday() != null) availabilityMap.put("monday", dto.getMonday());
        if (dto.getTuesday() != null) availabilityMap.put("tuesday", dto.getTuesday());
        if (dto.getWednesday() != null) availabilityMap.put("wednesday", dto.getWednesday());
        if (dto.getThursday() != null) availabilityMap.put("thursday", dto.getThursday());
        if (dto.getFriday() != null) availabilityMap.put("friday", dto.getFriday());
        if (dto.getSaturday() != null) availabilityMap.put("saturday", dto.getSaturday());
        if (dto.getSunday() != null) availabilityMap.put("sunday", dto.getSunday());
        return availabilityMap;
    }

    private PaymentInformation mapPaymentInfo(PaymentInfoDTO dto) {
        PaymentInformation paymentInfo = new PaymentInformation();
        paymentInfo.setAcceptsInsurance(dto.getAcceptsInsurance());
        paymentInfo.setAcceptedInsurances(new HashSet<>(dto.getAcceptedInsurances()));
        paymentInfo.setSessionFees(dto.getSessionFees());
        paymentInfo.setSlidingScaleAvailable(dto.getSlidingScaleAvailable());
        return paymentInfo;
    }

    public ProfessionalProfile getProfessionalProfileByUser(User user) {
        return professionalRepository.findByUser(user)
                .orElseThrow(() -> new CustomException("Professional profile not found"));
    }


    public ProfessionalProfile updateAvailability(Long professionalId, Map<String, String> availabilityMap) {
        ProfessionalProfile professionalProfile = professionalRepository.findById(professionalId)
                .orElseThrow(() -> new CustomException("Professional not found"));

        professionalProfile.getAvailability().setDailyAvailability(availabilityMap);
        return professionalRepository.save(professionalProfile);
    }

    public ProfessionalProfile addDocument(Long professionalId, String docType, String url) {
        ProfessionalProfile professionalProfile = getProfessionalById(professionalId);
        professionalProfile.getDocuments().put(docType, url);
        return professionalRepository.save(professionalProfile);
    }

    public ProfessionalProfile getProfessionalById(Long id) {
        return professionalRepository.findById(id)
                .orElseThrow(() -> new CustomException("Professional not found"));
    }

    public List<ProfessionalProfile> getRecommendedProfessionals(User user) {
        // Get latest assessment
        Assessment assessment = assessmentRepository.findTopByUserOrderByTimestampDesc(user)
                .orElseThrow(() -> new CustomException("No assessments found for user"));

        // Map risk level to specialties
        List<String> targetSpecialties = switch (assessment.getRiskLevel()) {
            case "CRISIS" -> Arrays.asList("Psychiatrist", "Emergency Counselor");
            case "HIGH" -> Arrays.asList("Psychiatrist", "Clinical Psychologist");
            case "MODERATE" -> Arrays.asList("Licensed Therapist", "Counselor");
            default -> Arrays.asList("Counselor", "Wellness Coach");
        };

//        return professionalRepository.findBySpecialtyInOrderByRatingDesc(targetSpecialties);
        return professionalRepository.findByExpertiseIn(targetSpecialties);
    }

    public List<ProfessionalProfile> filterProfessionals(
            String specialty,
            String location,
            Integer minRating,
            String availability) {

        Specification<ProfessionalProfile> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            query.distinct(true); // Prevent duplicate results

            if (specialty != null) {
                Join<ProfessionalProfile, String> expertiseJoin = root.join("experience.expertiseAreas");
                predicates.add((Predicate) cb.equal(expertiseJoin, specialty));
            }
            if (location != null) {
                predicates.add((Predicate) cb.like(root.get("address"), "%" + location + "%"));
            }
            if (minRating != null) {
                predicates.add((Predicate) cb.greaterThanOrEqualTo(root.get("rating"), minRating));
            }
            // Availability filtering removed (requires structural changes)

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return professionalRepository.findAll(spec);
    }

    public List<ProfessionalProfile> getAllProfessionals() {
        return professionalRepository.findAll(); // Remove invalid sort
    }
}