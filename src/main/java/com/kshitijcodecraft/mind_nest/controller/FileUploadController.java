package com.kshitijcodecraft.mind_nest.controller;

import com.kshitijcodecraft.mind_nest.dto.ApiResponse;
import com.kshitijcodecraft.mind_nest.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileStorageService fileStorageService;

    @PostMapping(value = "/upload-profile-pic", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<String>> uploadProfilePicture(
            @RequestPart("file") MultipartFile file
    ) {
        // Generate a unique path for this upload
        String uniqueId = UUID.randomUUID().toString();
        String path = "temp/" + uniqueId;

        // Upload to your storage with this path
        String imageUrl = fileStorageService.uploadFile(file);

        return ResponseEntity.ok(
                new ApiResponse<>("Profile picture uploaded", HttpStatus.OK.value(), imageUrl)
        );
    }
}
