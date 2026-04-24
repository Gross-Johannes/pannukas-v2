package net.teymm.pannukas.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import net.teymm.pannukas.category.CategoryType;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

public record CreateCategory(
        @NotBlank
        // TODO: replace with constant from AppConstants class
        @Length(max = 100)
        String title,

        @NotNull
        CategoryType categoryType,
        MultipartFile coverImage
) {
}
