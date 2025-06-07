package com.kshitijcodecraft.mind_nest.repository;

import com.kshitijcodecraft.mind_nest.entity.ProfessionalProfile;
import com.kshitijcodecraft.mind_nest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessionalRepository extends JpaRepository<ProfessionalProfile, Long>, JpaSpecificationExecutor<ProfessionalProfile> {
    Optional<ProfessionalProfile> findByUserId(Long userId);

    List<ProfessionalProfile> findByCredentials_ProfessionalTitleContainingIgnoreCase(String title);

    List<ProfessionalProfile> findByExperience_ExpertiseAreasContaining(String expertise);

    List<ProfessionalProfile> findByAvailability_AcceptingNewClientsTrue();

    Optional<ProfessionalProfile> findByUser(User user);


    @Query("SELECT p FROM ProfessionalProfile p WHERE " +
            "LOWER(p.credentials.professionalTitle) LIKE LOWER(concat('%', :query, '%')) OR " +
            ":query MEMBER OF p.experience.expertiseAreas")
    List<ProfessionalProfile> searchByTitleOrExpertise(@Param("query") String query);

    @Query("SELECT DISTINCT p FROM ProfessionalProfile p JOIN p.experience.expertiseAreas e WHERE e IN :specialties")
    List<ProfessionalProfile> findByExpertiseIn(@Param("specialties") List<String> specialties);


    List<ProfessionalProfile> findBySpecialtyInOrderByRatingDesc(List<String> specialties);
}