package com.example.services;

import com.example.models.ImageProductEntity;
import com.example.repositories.ImageProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ImageProductService extends GenericServicesImpl<ImageProductEntity, Long, ImageProductRepository>{

    public ImageProductService(ImageProductRepository repository) {
        super(repository);
    }

    @Transactional(readOnly = true)
    public List<ImageProductEntity> findByProductId(Long productId) {
        return repository.findByProductProductId(productId);
    }
}
