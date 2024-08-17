package com.example.controllers;

import com.example.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
        return ResponseEntity.ok(productService.findById(id));
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

    @GetMapping("/filter")
    public ResponseEntity<?> filterProducts(
            @RequestParam(required = false) List<String> subcategoryNames,
            @RequestParam(required = false) List<String> marcaNames,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {
        return ResponseEntity.ok(productService.filterProducts(subcategoryNames, marcaNames, minPrice, maxPrice));
    }

    @GetMapping("/top-views")
    public ResponseEntity<?> findTop5Views() {
        return ResponseEntity.ok(productService.finTop5Views());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProductWithImages(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam Long price,
            @RequestParam Long stock,
            @RequestParam Long marcaId,
            @RequestParam Long subcategoryId,
            @RequestParam List<MultipartFile> images) {
        try {
            productService.insert(name, description, price, stock, marcaId, subcategoryId, images);
            return ResponseEntity.ok("Product created");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
