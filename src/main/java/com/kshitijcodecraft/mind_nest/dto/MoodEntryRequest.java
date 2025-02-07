package com.kshitijcodecraft.mind_nest.dto;

import lombok.Data;

@Data
public class MoodEntryRequest {
    private int moodScore;
    private String notes;
}