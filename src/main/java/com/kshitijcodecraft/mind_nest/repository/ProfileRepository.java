package com.kshitijcodecraft.mind_nest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kshitijcodecraft.mind_nest.entity.User;
import com.kshitijcodecraft.mind_nest.entity.UserProfile;

@Repository
public interface ProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByUser(User user);

    @Query("SELECT p FROM UserProfile p WHERE p.user.id = :userId")
    Optional<UserProfile> findByUserId(@Param("userId") Long userId);

}