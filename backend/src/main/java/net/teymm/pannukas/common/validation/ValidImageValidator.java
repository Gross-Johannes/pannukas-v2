package net.teymm.pannukas.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import net.teymm.pannukas.config.AppConstants;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class ValidImageValidator implements ConstraintValidator<ValidImage, MultipartFile> {

    private static final Set<String> ALLOWED_CONTENT_TYPES = new HashSet<>(
            Arrays.asList(AppConstants.ALLOWED_IMAGE_CONTENT_TYPES)
    );

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            return true;
        }

        if (file.getSize() > AppConstants.MAX_IMAGE_SIZE_BYTES) {
            return false;
        }

        String contentType = file.getContentType();

        if (contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType.toLowerCase())) {
            return false;
        }

        try (InputStream inputStream = file.getInputStream()) {
            return matchesSignature(contentType, inputStream);
        } catch (IOException ex) {
            return false;
        }
    }

    private boolean matchesSignature(String contentType, InputStream inputStream) throws IOException {
        return switch (contentType.toLowerCase()) {
            case "image/jpeg", "image/jpg" -> isJpeg(inputStream);
            case "image/png" -> isPng(inputStream);
            case "image/webp" -> isWebp(inputStream);
            default -> false;
        };
    }

    private boolean isJpeg(InputStream inputStream) throws IOException {
        byte[] header = inputStream.readNBytes(3);

        return header.length == 3
                && (header[0] & 0xFF) == 0xFF
                && (header[1] & 0xFF) == 0xD8
                && (header[2] & 0xFF) == 0xFF;
    }

    private boolean isPng(InputStream inputStream) throws IOException {
        byte[] header = inputStream.readNBytes(8);
        byte[] pngSignature = new byte[]{(byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A};

        if (header.length != pngSignature.length) {
            return false;
        }

        for (int i = 0; i < pngSignature.length; i++) {
            if (header[i] != pngSignature[i]) {
                return false;
            }
        }

        return true;
    }

    private boolean isWebp(InputStream inputStream) throws IOException {
        byte[] header = inputStream.readNBytes(12);

        if (header.length != 12) {
            return false;
        }

        return header[0] == 'R'
                && header[1] == 'I'
                && header[2] == 'F'
                && header[3] == 'F'
                && header[8] == 'W'
                && header[9] == 'E'
                && header[10] == 'B'
                && header[11] == 'P';
    }
}
