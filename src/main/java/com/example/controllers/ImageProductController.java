package com.example.controllers;

import com.example.models.ImageProductEntity;
import com.example.services.ImageProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/image-products")
public class ImageProductController {

    private final ImageProductService imageProductService;

    @Autowired
    public ImageProductController(ImageProductService imageProductService) {
        this.imageProductService = imageProductService;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> findImagesByProductId(@PathVariable(value = "productId") Long productId) {
        return ResponseEntity.ok(imageProductService.findByProductId(productId));
    }


    @PostMapping
    public ResponseEntity<?> saveImageProduct(@RequestParam("file") MultipartFile file
            , @RequestParam Long productId) {
        try {
            ImageProductEntity saveImageProduct = imageProductService.saveImageProduct(file, productId);
            return ResponseEntity.ok(saveImageProduct);
        } catch (Exception e) {
            // Log the error
            System.err.println("Error while saving Image: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
