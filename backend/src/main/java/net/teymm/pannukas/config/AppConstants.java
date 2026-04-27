package net.teymm.pannukas.config;

public final class AppConstants {

    public static final int MIN_CATEGORY_TITLE_LENGTH = 2;
    public static final int MAX_CATEGORY_TITLE_LENGTH = 100;

    public static final long MAX_IMAGE_SIZE_BYTES = 5L * 1024 * 1024;
    public static final String[] ALLOWED_IMAGE_CONTENT_TYPES = {
            "image/jpeg",
            "image/jpg",
            "image/png",
            "image/webp"
    };
}
