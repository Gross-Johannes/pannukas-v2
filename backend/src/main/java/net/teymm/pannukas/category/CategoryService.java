package net.teymm.pannukas.category;

import net.teymm.pannukas.category.dto.CreateCategoryReq;
import net.teymm.pannukas.image.Image;
import net.teymm.pannukas.image.ImageType;
import net.teymm.pannukas.image.service.ImageService;
import net.teymm.pannukas.upload.enums.ImageFolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ImageService imageService;

    public CategoryService(CategoryRepository categoryRepository, ImageService imageService) {
        this.categoryRepository = categoryRepository;
        this.imageService = imageService;
    }

    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category createCategory(CreateCategoryReq request) {
        String title = request.title();
        CategoryType categoryType = CategoryType.valueOf(request.categoryType());
        MultipartFile coverImage = request.coverImage();

        Category category = new Category();

        category.setTitle(title);
        category.setCategoryType(categoryType);

        if (!coverImage.isEmpty()) {
            Image savedImage = imageService.saveAndUploadImage(
                    coverImage,
                    ImageFolder.CATEGORY,
                    title,
                    ImageType.CATEGORY,
                    true
            );
            category.setCoverImage(savedImage);
        }

        return categoryRepository.save(category);
    }
}
