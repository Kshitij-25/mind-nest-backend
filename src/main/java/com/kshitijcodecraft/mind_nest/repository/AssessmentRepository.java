package com.kshitijcodecraft.mind_nest.repository;

import com.kshitijcodecraft.mind_nest.entity.Assessment;
import com.kshitijcodecraft.mind_nest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
    List<Assessment> findByUser(User user);  // Correct repository method
}