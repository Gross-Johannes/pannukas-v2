package net.teymm.pannukas.category;

import net.teymm.pannukas.category.dto.CreateCategory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category createCategory(CreateCategory request) {
        Category category = new Category();

        category.setTitle(request.title());
        category.setCategoryType(CategoryType.valueOf(request.categoryType()));

        return categoryRepository.save(category);
    }
}
