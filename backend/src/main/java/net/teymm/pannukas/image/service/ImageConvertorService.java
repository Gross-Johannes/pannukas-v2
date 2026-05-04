package net.teymm.pannukas.image.service;

import net.coobird.thumbnailator.Thumbnails;
import net.teymm.pannukas.common.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;

@Service
public class ImageConvertorService {

    public byte[] convertToWebp(MultipartFile file) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            Thumbnails.of(file.getInputStream())
                    .size(1920, 1280)
                    .outputFormat("webp")
                    .outputQuality(0.8)
                    .toOutputStream(baos);

            return baos.toByteArray();
        } catch (Exception ex) {
            throw new ApiException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
