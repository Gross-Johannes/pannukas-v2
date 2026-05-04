package net.teymm.pannukas.config.properties;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app.s3")
public record AppS3Properties(

        @NotBlank(message = "'APP_S3_ACCESS_KEY' is required")
        String accessKey,

        @NotBlank(message = "'APP_S3_SECRET_KEY' is required")
        String secretKey,

        @NotBlank(message = "'APP_S3_REGION' is required")
        String region,

        @NotBlank(message = "'APP_S3_BUCKET_NAME' is required")
        String bucketName
) {
}
