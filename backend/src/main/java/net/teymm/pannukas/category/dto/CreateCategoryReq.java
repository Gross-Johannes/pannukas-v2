package net.teymm.pannukas.category.dto;

import jakarta.validation.constraints.NotBlank;
import net.teymm.pannukas.category.CategoryType;
import net.teymm.pannukas.category.validation.UniqueTitle;
import net.teymm.pannukas.common.validation.ValidImage;
import net.teymm.pannukas.common.validation.EnumValue;
import net.teymm.pannukas.config.AppConstants;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

public record CreateCategoryReq(
        @NotBlank
        @Length(
                min = AppConstants.MIN_CATEGORY_TITLE_LENGTH,
                max = AppConstants.MAX_CATEGORY_TITLE_LENGTH
        )
        @UniqueTitle
        String title,

        @NotBlank
        @EnumValue(enumClass = CategoryType.class)
        String categoryType,

        @ValidImage
        MultipartFile coverImage
) {
}
