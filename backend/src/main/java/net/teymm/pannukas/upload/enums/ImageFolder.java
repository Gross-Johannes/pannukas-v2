package net.teymm.pannukas.upload.enums;

public enum ImageFolder implements FolderType {
    CATEGORY("categories"),
    GALLERY("gallery"),
    EVENT("events");

    private final String folderName;

    ImageFolder(String folderName) {
        this.folderName = folderName;
    }

    @Override
    public String getFolderName() {
        return folderName;
    }
}
