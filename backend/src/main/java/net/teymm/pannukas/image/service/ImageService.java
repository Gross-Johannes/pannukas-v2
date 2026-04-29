package net.teymm.pannukas.image.service;

import net.teymm.pannukas.common.exception.ApiException;
import net.teymm.pannukas.image.Image;
import net.teymm.pannukas.image.ImageRepository;
import net.teymm.pannukas.image.ImageType;
import org.springframework.http.HttpStatus;
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

    public Image saveAndUploadImage(MultipartFile file, String altText, ImageType imageType, boolean visible) {
        Image savedImage = saveImage(altText, imageType, visible);
        uploadImage(file, savedImage.getId().toString());

        return savedImage;
    }

    public Image saveImage(String title, ImageType imageType, boolean visible) {
        Image image = new Image(title, imageType, visible);

        return imageRepository.save(image);
    }

    public void uploadImage(MultipartFile file, String fileName) {
        try {
            uploadService.uploadFile(file, fileName);
        } catch (Exception e) {
            throw new ApiException("Failed to upload image: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
