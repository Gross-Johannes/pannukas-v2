package net.teymm.pannukas.upload;

import net.teymm.pannukas.common.exception.ApiException;
import net.teymm.pannukas.config.AppConfig;
import net.teymm.pannukas.image.FileData;
import net.teymm.pannukas.upload.enums.FolderType;
import net.teymm.pannukas.upload.enums.StorageSection;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class UploadService {

    private final AppConfig appConfig;
    private final S3Client s3Client;

    public UploadService(AppConfig appConfig, S3Client s3Client) {
        this.appConfig = appConfig;
        this.s3Client = s3Client;
    }

    public void uploadFile(
            FileData file,
            StorageSection storageSection,
            FolderType folder
    ) {
        try {
            s3Client.putObject(PutObjectRequest.builder()
                    .bucket(appConfig.getS3Properties().bucketName())
                    .key(buildObjectKey(storageSection, folder, file.name()))
                    .build(), RequestBody.fromBytes(file.bytes()));
        } catch (Exception ex) {
            throw new ApiException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String buildObjectKey(StorageSection storageSection, FolderType folder, String fileName
    ) {
        return KeyConstructor.build(storageSection, folder, fileName);
    }
}
