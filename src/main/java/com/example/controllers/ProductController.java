package com.example.controllers;

import com.example.models.ProductEntity;
import com.example.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<?> findAllProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findProductById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(productService.findById(id, ProductEntity.class));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> findProductByName(@PathVariable(value = "name") String name) {
        return ResponseEntity.ok(productService.findByName(name));
    }

    @GetMapping("/subcategory/{subcategoryId}")
    public ResponseEntity<?> findProductsBySubcategoryId(@PathVariable(value = "subcategoryId") Long subcategoryId) {
        return ResponseEntity.ok(productService.findBySubcategoryId(subcategoryId));
    }

    @GetMapping("/marca/{marcaId}")
    public ResponseEntity<?> findProductsByMarcaId(@PathVariable(value = "marcaId") Long marcaId) {
        return ResponseEntity.ok(productService.findByMarcaId(marcaId));
    }

    @GetMapping("/subcategory/name/{subcategoryName}")
    public ResponseEntity<?> findProductsBySubcategoryName(@PathVariable(value = "subcategoryName") String subcategoryName) {
        return ResponseEntity.ok(productService.findBySubcategoryName(subcategoryName));
    }
}
