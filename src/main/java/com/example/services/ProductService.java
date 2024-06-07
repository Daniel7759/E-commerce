package com.example.services;

import com.example.exceptions.ResourceNotFoundException;
import com.example.models.ProductEntity;
import com.example.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService extends GenericServicesImpl<ProductEntity, Long, ProductRepository>{

    public ProductService(ProductRepository repository) {
        super(repository);
    }

    @Transactional(readOnly = true)
    public ProductEntity findByName(String name) {
        return repository.findByName(name)
                .orElseThrow(()-> new ResourceNotFoundException(ProductEntity.class.getSimpleName()+" not found with name "+name));
    }

    @Transactional(readOnly = true)
    public List<ProductEntity> findBySubcategoryId(Long subcategoryId) {
        return repository.findBySubcategorySubcategoryId(subcategoryId);
    }

    @Transactional(readOnly = true)
    public List<ProductEntity> findByMarcaId(Long marcaId) {
        return repository.findByMarcaMarcaId(marcaId);
    }

    @Transactional(readOnly = true)
    public List<ProductEntity> findBySubcategoryName(String subcategoryName) {
        return repository.findBySubcategoryName(subcategoryName);
    }
}
