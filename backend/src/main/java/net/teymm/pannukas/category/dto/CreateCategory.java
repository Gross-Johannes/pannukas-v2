package net.teymm.pannukas.category.dto;

import jakarta.validation.constraints.NotBlank;
import net.teymm.pannukas.category.CategoryType;
import net.teymm.pannukas.category.validation.UniqueTitle;
import net.teymm.pannukas.common.validation.EnumValue;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

public record CreateCategory(
        @NotBlank
        // TODO: replace with constant from AppConstants class
        @Length(min = 2, max = 100)
        @UniqueTitle
        String title,

        @NotBlank
        @EnumValue(enumClass = CategoryType.class)
        String categoryType,
        MultipartFile coverImage
) {
}
