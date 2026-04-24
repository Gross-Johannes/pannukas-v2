package net.teymm.pannukas.category;

import jakarta.servlet.http.HttpServletRequest;
import net.teymm.pannukas.common.response.ApiResponse;
import net.teymm.pannukas.common.response.ResponseFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
