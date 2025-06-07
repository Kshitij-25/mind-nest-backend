package com.kshitijcodecraft.mind_nest.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    /**
     * Uploads images or documents to S3 based on file type.
     *
     * @param file MultipartFile to upload
     * @return Pre-signed URL of the uploaded file
     */
    public String uploadFile(MultipartFile file) {
        validateFile(file);  // ✅ Validate file (image or document)

        String folder = isImage(file) ? "images/" : "docs/";  // ✅ Choose folder based on type
        String fileName = folder + generateFileName(file);

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            // Upload file to S3
            s3Client.putObject(bucketName, fileName, file.getInputStream(), metadata);

            // ✅ Generate pre-signed URL valid for 7 days
            return generatePresignedUrl(fileName);
        } catch (IOException e) {
            throw new RuntimeException("Error uploading file: " + e.getMessage(), e);
        }
    }

    /**
     * Generates a pre-signed URL valid for 7 days.
     */
    private String generatePresignedUrl(String fileName) {
        Date expiration = new Date(System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000));  // 7 days

        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, fileName)
                .withMethod(HttpMethod.GET)
                .withExpiration(expiration);

        URL url = s3Client.generatePresignedUrl(request);
        return url.toString();
    }

    /**
     * Validates uploaded file based on type.
     */
    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        if (file.getSize() > 10 * 1024 * 1024) {  // 10MB limit
            throw new RuntimeException("File size exceeds 10MB limit");
        }

        String contentType = file.getContentType();
        if (contentType == null || (!isImage(file) && !isDocument(file))) {
            throw new RuntimeException("Unsupported file type. Only images and documents are allowed.");
        }
    }

    /**
     * Checks if the file is an image.
     */
    private boolean isImage(MultipartFile file) {
        return file.getContentType() != null && file.getContentType().startsWith("image/");
    }

    /**
     * Checks if the file is a supported document.
     */
    private boolean isDocument(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && (
                contentType.equals("application/pdf") ||
                        contentType.equals("application/msword") ||
                        contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
        );
    }

    /**
     * Generates a unique file name.
     */
    private String generateFileName(MultipartFile file) {
        return UUID.randomUUID() + "-" + file.getOriginalFilename().replace(" ", "_");
    }
}
