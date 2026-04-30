package net.teymm.pannukas.upload;

import net.teymm.pannukas.config.AppConstants;
import net.teymm.pannukas.upload.enums.FolderType;
import net.teymm.pannukas.upload.enums.StorageSection;

public final class KeyConstructor {

    public static String build(
            StorageSection storageSection,
            FolderType folder,
            String fileName
    ) {
        return String.join("/",
                AppConstants.S3_BUCKET_APP_NAMESPACE,
                storageSection.getFolderName(),
                folder.getFolderName(),
                fileName
        );
    }
}
