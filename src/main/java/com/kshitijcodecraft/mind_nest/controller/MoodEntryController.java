package com.kshitijcodecraft.mind_nest.controller;

import com.kshitijcodecraft.mind_nest.dto.MoodEntryRequest;
import com.kshitijcodecraft.mind_nest.entity.MoodEntry;
import com.kshitijcodecraft.mind_nest.entity.User;
import com.kshitijcodecraft.mind_nest.service.MoodEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mood-entries")
@RequiredArgsConstructor
public class MoodEntryController {
    private final MoodEntryService moodEntryService;

    @PostMapping
    public ResponseEntity<MoodEntry> createMoodEntry(
            @RequestBody MoodEntryRequest request,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(moodEntryService.createMoodEntry(request, user));
    }

    @GetMapping
    public ResponseEntity<List<MoodEntry>> getMoodEntries(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(moodEntryService.getMoodEntries(user));
    }
}