package net.teymm.pannukas.image.service;

import net.teymm.pannukas.image.Image;
import net.teymm.pannukas.image.ImageRepository;
import net.teymm.pannukas.image.ImageType;
import net.teymm.pannukas.upload.UploadService;
import net.teymm.pannukas.upload.enums.ImageFolder;
import net.teymm.pannukas.upload.enums.StorageSection;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final UploadService uploadService;

    public ImageService(
            ImageRepository imageRepository,
            UploadService uploadService
    ) {
        this.imageRepository = imageRepository;
        this.uploadService = uploadService;
    }

    public Image saveAndUploadImage(
            MultipartFile file,
            ImageFolder imageFolder,
            String altText,
            ImageType imageType,
            boolean visible
    ) {
        Image savedImage = saveImage(altText, imageType, visible);
        uploadImage(file, imageFolder, savedImage.getId().toString());

        return savedImage;
    }

    private Image saveImage(String altText, ImageType imageType, boolean visible) {
        Image image = new Image(altText, imageType, visible);

        return imageRepository.save(image);
    }

    private void uploadImage(MultipartFile file, ImageFolder imageFolder, String fileName) {
        uploadService.uploadFile(file, StorageSection.IMAGES, imageFolder, fileName);
    }
}
