package com.example.controllers;

import com.example.models.SubcategoryEntity;
import com.example.services.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subcategories")
public class SubcategoryController {

    private final SubcategoryService subcategoryService;

    @Autowired
    public SubcategoryController(SubcategoryService subcategoryService) {
        this.subcategoryService = subcategoryService;
    }

    @GetMapping
    public ResponseEntity<?> findAllSubcategories() {
        return ResponseEntity.ok(subcategoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findSubcategoryById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(subcategoryService.findById(id, SubcategoryEntity.class));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> findSubcategoryByName(@PathVariable(value = "name") String name) {
        return ResponseEntity.ok(subcategoryService.findByName(name));
    }
}
