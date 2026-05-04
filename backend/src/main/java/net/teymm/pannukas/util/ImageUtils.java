package net.teymm.pannukas.util;

public final class ImageUtils {

    private ImageUtils() {
    }

    public static boolean hasExtension(String originalFilename) {
        if (originalFilename == null || originalFilename.isBlank()) {
            return false;
        }

        int lastDotIndex = originalFilename.lastIndexOf('.');

        return lastDotIndex > 0 && lastDotIndex < originalFilename.length() - 1;
    }

    public static String getExtension(String originalFilename) {
        if (!hasExtension(originalFilename)) {
            return "";
        }

        int lastDotIndex = originalFilename.lastIndexOf('.');

        return originalFilename.substring(lastDotIndex + 1).toLowerCase();
    }
}
