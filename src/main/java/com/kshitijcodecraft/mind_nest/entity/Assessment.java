package com.kshitijcodecraft.mind_nest.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
public class Assessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int score;
    private LocalDateTime timestamp;
    private String assessmentType; // e.g., "PHQ-9", "GAD-7"
    private String responses; // JSON string of user's answers (optional)

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}