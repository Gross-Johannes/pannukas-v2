package net.teymm.pannukas.common.validation;

import net.teymm.pannukas.config.AppConstants;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidImageValidatorTest {

    private final ValidImageValidator validator = new ValidImageValidator();

    @Test
    void acceptsNullOrEmptyFiles() {
        assertTrue(validator.isValid(null, null));
        assertTrue(validator.isValid(new MockMultipartFile("coverImage", "", null, new byte[0]), null));
    }

    @Test
    void acceptsAllowedContentTypesWithinSizeLimit() {
        assertTrue(validator.isValid(
                new MockMultipartFile("coverImage", "cover.png", "image/png",
                        new byte[]{(byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A, 0x00}),
                null
        ));
        assertTrue(validator.isValid(
                new MockMultipartFile("coverImage", "cover.webp", "image/webp",
                        new byte[]{'R', 'I', 'F', 'F', 0x00, 0x00, 0x00, 0x00, 'W', 'E', 'B', 'P', 'V', 'P', '8', ' '}),
                null
        ));
        assertTrue(validator.isValid(
                new MockMultipartFile("coverImage", "cover.jpg", "image/jpeg",
                        new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF, 0x00}),
                null
        ));
    }

    @Test
    void rejectsUnsupportedContentType() {
        assertFalse(validator.isValid(
                new MockMultipartFile("coverImage", "cover.txt", "text/plain", new byte[]{1, 2, 3}),
                null
        ));
    }

    @Test
    void rejectsSpoofedContentTypeWithInvalidSignature() {
        assertFalse(validator.isValid(
                new MockMultipartFile("coverImage", "cover.png", "image/png", new byte[]{1, 2, 3, 4}),
                null
        ));
    }

    @Test
    void acceptsPngWithValidSignature() {
        byte[] pngHeader = new byte[]{(byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A, 0x00};

        assertTrue(validator.isValid(
                new MockMultipartFile("coverImage", "cover.png", "image/png", pngHeader),
                null
        ));
    }

    @Test
    void rejectsOversizedFile() {
        byte[] bytes = new byte[(int) AppConstants.MAX_IMAGE_SIZE_BYTES + 1];

        assertFalse(validator.isValid(
                new MockMultipartFile("coverImage", "cover.png", "image/png", bytes),
                null
        ));
    }
}
