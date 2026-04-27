package net.teymm.pannukas.image.service;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
public class ImageConvertorService {

    public void test(MultipartFile file) throws IOException {
        BufferedImage image = Thumbnails.of(file.getInputStream())
                .size(1200, 1200)
                .outputFormat("webp")
                .outputQuality(0.8)
                .asBufferedImage();
    }
}
