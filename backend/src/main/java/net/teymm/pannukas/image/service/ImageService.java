package net.teymm.pannukas.image.service;

import net.teymm.pannukas.image.FileData;
import net.teymm.pannukas.image.Image;
import net.teymm.pannukas.image.ImageRepository;
import net.teymm.pannukas.image.ImageType;
import net.teymm.pannukas.upload.UploadService;
import net.teymm.pannukas.upload.enums.ImageFolder;
import net.teymm.pannukas.upload.enums.StorageSection;
import net.teymm.pannukas.util.ImageUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final ImageConvertorService imageConvertorService;
    private final UploadService uploadService;

    public ImageService(
            ImageRepository imageRepository,
            ImageConvertorService imageConvertorService,
            UploadService uploadService
    ) {
        this.imageRepository = imageRepository;
        this.imageConvertorService = imageConvertorService;
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
        byte[] webpBytes = imageConvertorService.convertToWebp(file);

        String originalFilename = file.getOriginalFilename();
        String newFileName = savedImage.getId().toString();
        String newFileNameWithExtension = ImageUtils.hasExtension(originalFilename)
                ? newFileName + ".webp"
                : newFileName;

        FileData fileData = new FileData(webpBytes, newFileNameWithExtension);
        uploadImage(fileData, imageFolder);

        return savedImage;
    }

    private Image saveImage(String altText, ImageType imageType, boolean visible) {
        Image image = new Image(altText, imageType, visible);

        return imageRepository.save(image);
    }

    private void uploadImage(FileData file, ImageFolder imageFolder) {
        uploadService.uploadFile(file, StorageSection.IMAGES, imageFolder);
    }
}
