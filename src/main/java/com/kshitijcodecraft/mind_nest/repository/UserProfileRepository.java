package com.kshitijcodecraft.mind_nest.repository;

import com.kshitijcodecraft.mind_nest.entity.UserProfile;
import com.kshitijcodecraft.mind_nest.enums.Enums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    // Find by email (useful for checking duplicates)
    Optional<UserProfile> findByEmail(String email);

    // Find by phone number
    Optional<UserProfile> findByPhoneNumber(String phoneNumber);

    // Find by email and check if exists
    boolean existsByEmail(String email);

    // Find by phone number and check if exists
    boolean existsByPhoneNumber(String phoneNumber);

    // Find profiles by city and state
    List<UserProfile> findByCityAndState(String city, String state);

    // Find profiles by preferred language
    List<UserProfile> findByPreferredLanguage(String preferredLanguage);

    // Find profiles by preferred therapy mode
    List<UserProfile> findByPreferredMode(Enums.TherapyMode preferredMode);

    // Find profiles by age greater than or equal to
//    @Query("SELECT p FROM UserProfile p WHERE FUNCTION('DATEDIFF', YEAR, p.dateOfBirth, CURRENT_DATE) >= :age")
//    List<UserProfile> findByAgeGreaterThanEqual(@Param("age") int age);
}