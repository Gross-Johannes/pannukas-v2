package net.teymm.pannukas.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import net.teymm.pannukas.category.CategoryType;
import net.teymm.pannukas.category.validation.UniqueTitle;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

public record CreateCategory(
        @NotBlank
        // TODO: replace with constant from AppConstants class
        @Length(min = 2, max = 100)
        @UniqueTitle
        String title,

        @NotNull
        CategoryType categoryType,
        MultipartFile coverImage
) {
}
