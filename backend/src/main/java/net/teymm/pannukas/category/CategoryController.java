package net.teymm.pannukas.category;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import net.teymm.pannukas.category.dto.CreateCategoryReq;
import net.teymm.pannukas.common.response.ApiResponse;
import net.teymm.pannukas.common.response.ResponseFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategories(
            HttpServletRequest httpRequest
    ) {
        List<Category> data = categoryService.getAllCategories();
        HttpStatus status = HttpStatus.OK;

        ApiResponse<List<Category>> response = ResponseFactory.success(
                "Categories retrieved successfully",
                data,
                status,
                httpRequest
        );

        return new ResponseEntity<>(response, status);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Category>> createCategory(
            @ModelAttribute @Valid CreateCategoryReq createCategory,
            HttpServletRequest httpRequest
    ) {
        Category data = categoryService.createCategory(createCategory);
        HttpStatus status = HttpStatus.CREATED;

        ApiResponse<Category> response = ResponseFactory.success(
                "Category created successfully",
                data,
                status,
                httpRequest
        );

        return new ResponseEntity<>(response, status);
    }
}
