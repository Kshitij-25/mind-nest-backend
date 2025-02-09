package com.kshitijcodecraft.mind_nest.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.kshitijcodecraft.mind_nest.exception.CustomException;
import io.jsonwebtoken.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Service
public class FileStorageService {

    private final AmazonS3 s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public FileStorageService(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        // Upload file
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        try {
            s3Client.putObject(bucketName, fileName, file.getInputStream(), metadata);

        // Generate pre-signed URL valid for 7 days
        Date expiration = new Date();
        expiration.setTime(expiration.getTime() + (7 * 24 * 60 * 60 * 1000));

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, fileName)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(expiration);

        URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateFileName(MultipartFile file) {
        return UUID.randomUUID().toString() + "-" +
                file.getOriginalFilename().replace(" ", "_");
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new CustomException("File is empty");
        }

        if (file.getSize() > 5 * 1024 * 1024) { // 5MB
            throw new CustomException("File size exceeds 5MB limit");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new CustomException("Only image files are allowed");
        }
    }
}