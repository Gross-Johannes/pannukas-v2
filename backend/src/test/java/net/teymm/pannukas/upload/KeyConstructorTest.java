package net.teymm.pannukas.upload;

import net.teymm.pannukas.config.AppConstants;
import net.teymm.pannukas.upload.enums.ImageFolder;
import net.teymm.pannukas.upload.enums.StorageSection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KeyConstructorTest {

    @Test
    void buildAssemblesExpectedS3Key() {
        String key = KeyConstructor.build(StorageSection.IMAGES, ImageFolder.CATEGORY, "cover.jpg");

        assertEquals(AppConstants.S3_BUCKET_APP_NAMESPACE + "/images/categories/cover.jpg", key);
    }
}
