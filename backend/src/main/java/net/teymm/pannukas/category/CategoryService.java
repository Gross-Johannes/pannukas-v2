package net.teymm.pannukas.category;

import net.teymm.pannukas.category.dto.CreateCategoryReq;
import net.teymm.pannukas.common.exception.ApiException;
import net.teymm.pannukas.image.Image;
import net.teymm.pannukas.image.ImageRepository;
import net.teymm.pannukas.image.ImageType;
import net.teymm.pannukas.image.service.ImageConvertorService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final ImageConvertorService imageConvertorService;

    public CategoryService(CategoryRepository categoryRepository,
                           ImageRepository imageRepository,
                           ImageConvertorService imageConvertorService
    ) {
        this.categoryRepository = categoryRepository;
        this.imageRepository = imageRepository;
        this.imageConvertorService = imageConvertorService;
    }

    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category createCategory(CreateCategoryReq request) {
        Category category = new Category();

        category.setTitle(request.title());
        category.setCategoryType(CategoryType.valueOf(request.categoryType()));

        if (request.coverImage() != null && !request.coverImage().isEmpty()) {
            try {
                imageConvertorService.test(request.coverImage());
            } catch (Exception e) {
                System.out.println("Image conversion failed: " + e.getMessage());
                throw new ApiException("Failed to convert category cover image", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            Image image = new Image(request.title(), ImageType.CATEGORY, true);
            image = imageRepository.save(image);

            category.setCoverImage(image);
        }

        return categoryRepository.save(category);
    }
}
