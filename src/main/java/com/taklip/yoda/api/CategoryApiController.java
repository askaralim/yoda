package com.taklip.yoda.api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taklip.yoda.model.Category;
import com.taklip.yoda.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/category")
@Tag(name = "Category Management", description = "Category management API endpoints")
public class CategoryApiController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    @Operation(summary = "Get categories", description = "Get categories by page")
    public ResponseEntity<Page<Category>> getCategories(
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "10") Integer limit) {
        return ResponseEntity.ok(categoryService.getByPage(offset, limit));
    }

    @GetMapping("/all")
    @Operation(summary = "Get all categories", description = "Get all categories")
    public ResponseEntity<List<Category>> allCategories() {
        return ResponseEntity.ok(categoryService.getCategories());
    }

    @PostMapping
    @Operation(summary = "Create category", description = "Create category")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(category));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update category", description = "Update category by category id")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id,
            @RequestBody Category category) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.update(category));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete category", description = "Delete category by category id")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
