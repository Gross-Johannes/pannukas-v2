package net.teymm.pannukas.upload;

import net.teymm.pannukas.common.exception.ApiException;
import net.teymm.pannukas.upload.enums.FolderType;
import net.teymm.pannukas.upload.enums.StorageSection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class UploadService {

    private final S3Client s3Client;

    @Value("${aws.bucketName}")
    private String bucketName;

    public UploadService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public void uploadFile(
            MultipartFile file,
            StorageSection storageSection,
            FolderType folder,
            String fileName
    ) {
        try {
            s3Client.putObject(PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(buildObjectKey(storageSection, folder, file, fileName))
                    .build(), RequestBody.fromBytes(file.getBytes()));
        } catch (Exception ex) {
            throw new ApiException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String buildObjectKey(
            StorageSection storageSection,
            FolderType folder,
            MultipartFile file,
            String fileName
    ) {
        String originalFilename = file.getOriginalFilename();
        String fileNameWithExtension = hasExtension(originalFilename)
                ? fileName + "." + getExtension(originalFilename)
                : fileName;

        return KeyConstructor.build(storageSection, folder, fileNameWithExtension);
    }

    private boolean hasExtension(String originalFilename) {
        if (originalFilename == null || originalFilename.isBlank()) {
            return false;
        }

        int lastDotIndex = originalFilename.lastIndexOf('.');

        return lastDotIndex > 0 && lastDotIndex < originalFilename.length() - 1;
    }

    private String getExtension(String originalFilename) {
        if (!hasExtension(originalFilename)) {
            return "";
        }

        int lastDotIndex = originalFilename.lastIndexOf('.');

        return originalFilename.substring(lastDotIndex + 1).toLowerCase();
    }
}
