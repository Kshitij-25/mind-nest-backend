package com.kshitijcodecraft.mind_nest.dto.professionals;

import lombok.Data;

import java.util.List;

@Data
public class EducationDTO {
    private String degree;
    private String institution;
    private Integer graduationYear;
    private List<String> specializations; // Now using List<String>
}