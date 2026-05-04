package net.teymm.pannukas.config.properties;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AppS3PropertiesTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void closeValidator() {
        validatorFactory.close();
    }

    @Test
    void validPropertiesProduceNoViolations() {
        AppS3Properties props = new AppS3Properties(
                "test-access-key",
                "test-secret-key",
                "us-east-1",
                "test-bucket"
        );

        Set<ConstraintViolation<AppS3Properties>> violations = validator.validate(props);
        assertTrue(violations.isEmpty(), "Expected no validation violations for valid properties");
    }

    @Test
    void blankPropertiesProduceViolationsWithCustomMessages() {
        AppS3Properties props = new AppS3Properties(
                "",
                " ",
                "",
                ""
        );

        Set<ConstraintViolation<AppS3Properties>> violations = validator.validate(props);

        assertEquals(4, violations.size());

        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("'APP_S3_ACCESS_KEY' is required")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("'APP_S3_SECRET_KEY' is required")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("'APP_S3_REGION' is required")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("'APP_S3_BUCKET_NAME' is required")));
    }
}
