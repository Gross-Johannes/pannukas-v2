package net.teymm.pannukas.config;

import net.teymm.pannukas.config.properties.AppS3Properties;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {
    /**
     * Strongly-typed configuration properties bound from application config
     * (application.yml / application.properties / environment variables).
     */
    private final AppS3Properties s3Properties;

    public AppConfig(AppS3Properties s3Properties) {
        this.s3Properties = s3Properties;
    }

    public AppS3Properties getS3Properties() {
        return s3Properties;
    }
}
