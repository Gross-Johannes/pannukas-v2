package net.teymm.pannukas.upload.enums;

public enum StorageSection {
    IMAGES("images");

    private final String folderName;

    StorageSection(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderName() {
        return folderName;
    }
}
