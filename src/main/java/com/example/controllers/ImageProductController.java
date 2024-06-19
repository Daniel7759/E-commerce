package com.example.controllers;

import com.example.models.ImageProductEntity;
import com.example.services.ImageProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/image-products")
public class ImageProductController {

    private final ImageProductService imageProductService;

    @Autowired
    public ImageProductController(ImageProductService imageProductService) {
        this.imageProductService = imageProductService;
    }

    @PostMapping
    public ResponseEntity<?> saveImageProduct(@RequestParam("file") MultipartFile file
            , @RequestParam String imageProduct) {
        try {
            //Specify the folder in Firebase Cloud Storage
            String folder = "image-products/";

            // Upload the file to Firebase Cloud Storage
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String fullFileName = folder + fileName;
            Bucket bucket = StorageClient.getInstance().bucket();
            Blob blob = bucket.create(fullFileName, file.getInputStream(), file.getContentType());

            // Get the download URL of the uploaded file
            String imageUrl = String.format("https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media", bucket.getName(), URLEncoder.encode(blob.getName(), StandardCharsets.UTF_8.name()));

            // Parse the imageProduct JSON
            ImageProductEntity imageProductEntity = new ObjectMapper().readValue(imageProduct, ImageProductEntity.class);

            // Set the image URL of the imageProduct
            imageProductEntity.setImageUrl(imageUrl);

            // Save the imageProduct to the database
            imageProductService.insert(imageProductEntity);

            return ResponseEntity.ok(imageProductEntity);
        } catch (Exception e) {
            // Log the error
            System.err.println("Error while saving marca: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
