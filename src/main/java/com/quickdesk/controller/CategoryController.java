package com.quickdesk.controller;



import com.quickdesk.model.Category;
import com.quickdesk.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepo;

    // Create new category
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category saved = categoryRepo.save(category);
        return ResponseEntity.ok(saved);
    }

    //  Get all categories
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryRepo.findAll());
    }
}
