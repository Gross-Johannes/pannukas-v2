package net.teymm.pannukas.image.service;

import net.teymm.pannukas.common.exception.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Service
public class UploadService {

    private final S3Client s3Client;

    @Value("${aws.bucketName}")
    private String bucketName;

    public UploadService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public void uploadFile(MultipartFile file, String fileName) {
        try {
            String key = "pannukas-v2/" + fileName;

            s3Client.putObject(PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build(), RequestBody.fromBytes(file.getBytes()));
        } catch (Exception e) {
            throw new ApiException("Failed to upload file: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void uploadFile(MultipartFile file, String folderPath, String fileName) throws IOException {
        String key = folderPath.endsWith("/") ? folderPath + fileName : folderPath + "/" + fileName;
        try {
            s3Client.putObject(PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build(), RequestBody.fromBytes(file.getBytes()));
        } catch (Exception e) {
            throw e;
        }
    }
}
