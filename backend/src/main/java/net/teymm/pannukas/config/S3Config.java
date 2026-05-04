package net.teymm.pannukas.config;

import net.teymm.pannukas.config.properties.AppS3Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

    private final AppConfig appConfig;

    public S3Config(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Bean
    public S3Client s3Client() {
        AppS3Properties s3Properties = appConfig.getS3Properties();
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(s3Properties.accessKey(), s3Properties.secretKey());

        return S3Client.builder()
                .region(Region.of(s3Properties.region()))
                .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
                .build();
    }
}
