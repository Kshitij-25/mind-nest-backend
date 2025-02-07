package com.kshitijcodecraft.mind_nest.repository;

import com.kshitijcodecraft.mind_nest.entity.MoodEntry;
import com.kshitijcodecraft.mind_nest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MoodEntryRepository extends JpaRepository<MoodEntry, Long> {
    List<MoodEntry> findByUser(User user);
}