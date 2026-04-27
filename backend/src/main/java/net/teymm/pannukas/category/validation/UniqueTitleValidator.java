package net.teymm.pannukas.category.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import net.teymm.pannukas.category.CategoryRepository;

public class UniqueTitleValidator implements ConstraintValidator<UniqueTitle, String> {

    private final CategoryRepository categoryRepository;

    public UniqueTitleValidator(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public boolean isValid(String title, ConstraintValidatorContext context) {
        if (title == null || title.isBlank()) {
            return true; // Let @NotBlank handle this case
        }

        return !categoryRepository.existsByTitleIgnoreCase(title);
    }
}
