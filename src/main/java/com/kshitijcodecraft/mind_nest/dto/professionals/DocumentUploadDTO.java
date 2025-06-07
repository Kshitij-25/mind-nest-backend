package com.kshitijcodecraft.mind_nest.dto.professionals;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class DocumentUploadDTO {
    private String documentType; // LICENSE, ID, CV, etc.
    private MultipartFile file;
}