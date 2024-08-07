package com.example.services;

import com.example.models.ImageProductEntity;
import com.example.models.ProductEntity;
import com.example.repositories.ImageProductRepository;
import com.example.repositories.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@Service
public class ImageProductService extends GenericServicesImpl<ImageProductEntity, Long, ImageProductRepository>{

    private final ProductRepository productRepository;

    public ImageProductService(ImageProductRepository repository, ProductRepository productRepository) {
        super(repository);
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<ImageProductEntity> findByProductId(Long productId) {
        return repository.findByProductProductId(productId);
    }

    public ImageProductEntity saveImageProduct(MultipartFile file, Long productId) throws Exception {
        // Specify the folder in Firebase Cloud Storage
        String folder = "image-products/";

        // Upload the file to Firebase Cloud Storage
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String fullFileName = folder + fileName;
        Bucket bucket = StorageClient.getInstance().bucket();
        Blob blob = bucket.create(fullFileName, file.getInputStream(), file.getContentType());

        // Get the download URL of the uploaded file
        String imageUrl = String.format("https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media", bucket.getName(), URLEncoder.encode(blob.getName(), StandardCharsets.UTF_8));

        // Parse the imageProduct JSON
        ProductEntity productEntity = productRepository.findById(productId).orElseThrow(() -> new Exception("Product not found"));

        // Set the image URL of the imageProduct
        ImageProductEntity imageProductEntity = new ImageProductEntity();
        imageProductEntity.setImageUrl(imageUrl);
        imageProductEntity.setProduct(productEntity);

        // Save the imageProduct to the database
        return repository.save(imageProductEntity);
    }
}
