package net.teymm.pannukas.upload;

import net.teymm.pannukas.common.exception.ApiException;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UploadServiceTest {

    @Mock
    private S3Client s3Client;

    @InjectMocks
    private UploadService uploadService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(uploadService, "bucketName", "test-bucket");
    }

    @Test
    void uploadFileBuildsExpectedKeyWithLowercasedExtension() {
        MultipartFile file = new MockMultipartFile(
                "coverImage",
                "cover.JPG",
                "image/jpeg",
                new byte[]{1, 2, 3}
        );

        uploadService.uploadFile(file, StorageSection.IMAGES, ImageFolder.CATEGORY, "category-cover");

        ArgumentCaptor<PutObjectRequest> requestCaptor = ArgumentCaptor.forClass(PutObjectRequest.class);
        verify(s3Client).putObject(requestCaptor.capture(), any(RequestBody.class));

        PutObjectRequest request = requestCaptor.getValue();
        assertEquals("test-bucket", request.bucket());
        assertEquals("pannukas-v2/images/categories/category-cover.jpg", request.key());
    }

    @Test
    void uploadFileDoesNotAppendTrailingDotWhenOriginalFileHasNoExtension() {
        MultipartFile file = new MockMultipartFile(
                "coverImage",
                "cover",
                "image/jpeg",
                new byte[]{4, 5, 6}
        );

        uploadService.uploadFile(file, StorageSection.IMAGES, ImageFolder.CATEGORY, "category-cover");

        ArgumentCaptor<PutObjectRequest> requestCaptor = ArgumentCaptor.forClass(PutObjectRequest.class);
        verify(s3Client).putObject(requestCaptor.capture(), any(RequestBody.class));

        PutObjectRequest request = requestCaptor.getValue();
        assertEquals("test-bucket", request.bucket());
        assertEquals("pannukas-v2/images/categories/category-cover", request.key());
    }

    @Test
    void uploadFileWrapsS3FailuresInApiException() {
        MultipartFile file = new MockMultipartFile(
                "coverImage",
                "cover.png",
                "image/png",
                new byte[]{7, 8, 9}
        );
        doThrow(new RuntimeException("boom")).when(s3Client).putObject(any(PutObjectRequest.class), any(RequestBody.class));

        ApiException exception = assertThrows(ApiException.class,
                () -> uploadService.uploadFile(file, StorageSection.IMAGES, ImageFolder.CATEGORY, "category-cover"));

        assertEquals("boom", exception.getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
    }

    @Test
    void uploadFileWrapsFileReadFailuresInApiException() throws IOException {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getBytes()).thenThrow(new IOException("cannot read bytes"));

        ApiException exception = assertThrows(ApiException.class,
                () -> uploadService.uploadFile(file, StorageSection.IMAGES, ImageFolder.CATEGORY, "category-cover"));

        assertEquals("cannot read bytes", exception.getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        verifyNoInteractions(s3Client);
    }
}
