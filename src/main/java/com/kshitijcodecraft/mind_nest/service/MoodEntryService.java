package com.kshitijcodecraft.mind_nest.service;

import com.kshitijcodecraft.mind_nest.dto.MoodEntryRequest;
import com.kshitijcodecraft.mind_nest.entity.MoodEntry;
import com.kshitijcodecraft.mind_nest.entity.User;
import com.kshitijcodecraft.mind_nest.repository.MoodEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MoodEntryService {
    private final MoodEntryRepository moodEntryRepository;

    public MoodEntry createMoodEntry(MoodEntryRequest request, User user) {
        MoodEntry entry = new MoodEntry();
        entry.setMoodScore(request.getMoodScore());
        entry.setNotes(request.getNotes());
        entry.setTimestamp(LocalDateTime.now());
        entry.setUser(user);
        return moodEntryRepository.save(entry);
    }

    public List<MoodEntry> getMoodEntries(User user) {
        return moodEntryRepository.findByUser(user);
    }
}