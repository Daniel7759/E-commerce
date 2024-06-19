package com.example.controllers;

import com.example.models.MarcaEntity;
import com.example.services.MarcaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/marcas")
public class MarcaController {

    private final MarcaService marcaService;

    @Autowired
    public MarcaController(MarcaService marcaService) {
        this.marcaService = marcaService;
    }

    @GetMapping
    public ResponseEntity<?> findAllMarcas() {
        return ResponseEntity.ok(marcaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findMarcaById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(marcaService.findById(id, MarcaEntity.class));
    }

    @PostMapping
    public ResponseEntity<?> saveMarca(@RequestParam("file") MultipartFile file
            , @RequestParam String marca) {
        try {
            // Upload the file to Firebase Cloud Storage
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Bucket bucket = StorageClient.getInstance().bucket();
            Blob blob = bucket.create(fileName, file.getInputStream(), file.getContentType());

            // Get the download URL of the uploaded file
            String logoUrl = String.format("https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media", bucket.getName(), URLEncoder.encode(blob.getName(), StandardCharsets.UTF_8.name()));

            // Parse the marca JSON
            MarcaEntity marcaEntity = new ObjectMapper().readValue(marca, MarcaEntity.class);

            // Set the logo URL of the marca
            marcaEntity.setLogo(logoUrl);

            // Save the marca to the database
            marcaService.insert(marcaEntity);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // Log the error
            System.err.println("Error while saving marca: " + e.getMessage());
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
