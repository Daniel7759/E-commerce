package com.example.controllers;

import com.example.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<?> findAllCategories() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findCategoryById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> findCategoryByName(@PathVariable(value = "name") String name) {
        return ResponseEntity.ok(categoryService.findByName(name));
    }
}
