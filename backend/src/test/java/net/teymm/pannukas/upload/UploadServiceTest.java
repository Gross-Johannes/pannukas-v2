package net.teymm.pannukas.upload;

import net.teymm.pannukas.common.exception.ApiException;
import net.teymm.pannukas.config.AppConfig;
import net.teymm.pannukas.config.properties.AppS3Properties;
import net.teymm.pannukas.image.FileData;
import net.teymm.pannukas.upload.enums.ImageFolder;
import net.teymm.pannukas.upload.enums.StorageSection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UploadServiceTest {

    @Mock
    private S3Client s3Client;

    @Mock
    private AppConfig appConfig;

    @InjectMocks
    private UploadService uploadService;

    @BeforeEach
    void setUp() {
        when(appConfig.getS3Properties()).thenReturn(new AppS3Properties(
                "access",
                "secret",
                "region",
                "test-bucket"
        ));
    }

    @Test
    void uploadFileBuildsExpectedKeyUsingProvidedFileName() {
        FileData file = new FileData(new byte[]{1, 2, 3}, "cover.JPG");

        uploadService.uploadFile(file, StorageSection.IMAGES, ImageFolder.CATEGORY);

        ArgumentCaptor<PutObjectRequest> requestCaptor = ArgumentCaptor.forClass(PutObjectRequest.class);
        verify(s3Client).putObject(requestCaptor.capture(), any(RequestBody.class));

        PutObjectRequest request = requestCaptor.getValue();
        assertEquals("test-bucket", request.bucket());
        assertEquals("pannukas-v2/images/categories/cover.JPG", request.key());
    }

    @Test
    void uploadFileBuildsExpectedKeyWhenFileNameHasNoExtension() {
        FileData file = new FileData(new byte[]{4, 5, 6}, "cover");

        uploadService.uploadFile(file, StorageSection.IMAGES, ImageFolder.CATEGORY);

        ArgumentCaptor<PutObjectRequest> requestCaptor = ArgumentCaptor.forClass(PutObjectRequest.class);
        verify(s3Client).putObject(requestCaptor.capture(), any(RequestBody.class));

        PutObjectRequest request = requestCaptor.getValue();
        assertEquals("test-bucket", request.bucket());
        assertEquals("pannukas-v2/images/categories/cover", request.key());
    }

    @Test
    void uploadFileWrapsS3FailuresInApiException() {
        FileData file = new FileData(new byte[]{7, 8, 9}, "cover.png");
        doThrow(new RuntimeException("boom")).when(s3Client).putObject(any(PutObjectRequest.class), any(RequestBody.class));

        ApiException exception = assertThrows(ApiException.class,
                () -> uploadService.uploadFile(file, StorageSection.IMAGES, ImageFolder.CATEGORY));

        assertEquals("boom", exception.getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
    }
}
