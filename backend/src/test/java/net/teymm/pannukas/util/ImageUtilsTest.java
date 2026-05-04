package net.teymm.pannukas.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ImageUtilsTest {

    @Test
    void hasExtensionReturnsFalseForMissingOrInvalidNames() {
        assertFalse(ImageUtils.hasExtension(null));
        assertFalse(ImageUtils.hasExtension(""));
        assertFalse(ImageUtils.hasExtension("   "));
        assertFalse(ImageUtils.hasExtension("cover"));
        assertFalse(ImageUtils.hasExtension(".env"));
        assertFalse(ImageUtils.hasExtension("cover."));
    }

    @Test
    void hasExtensionReturnsTrueForValidFileNames() {
        assertTrue(ImageUtils.hasExtension("cover.jpg"));
        assertTrue(ImageUtils.hasExtension("archive.tar.gz"));
    }

    @Test
    void getExtensionReturnsEmptyStringForMissingOrInvalidNames() {
        assertEquals("", ImageUtils.getExtension(null));
        assertEquals("", ImageUtils.getExtension(""));
        assertEquals("", ImageUtils.getExtension("   "));
        assertEquals("", ImageUtils.getExtension("cover"));
        assertEquals("", ImageUtils.getExtension(".env"));
        assertEquals("", ImageUtils.getExtension("cover."));
    }

    @Test
    void getExtensionReturnsLowerCasedLastExtension() {
        assertEquals("jpg", ImageUtils.getExtension("cover.JPG"));
        assertEquals("gz", ImageUtils.getExtension("archive.tar.GZ"));
        assertEquals("png", ImageUtils.getExtension("my.photo.png"));
    }
}
