package com.example.services;

import com.example.exceptions.ResourceNotFoundException;
import com.example.models.SubcategoryEntity;
import com.example.repositories.SubcategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class SubcategoryService extends GenericServicesImpl<SubcategoryEntity, Long, SubcategoryRepository>{

    public SubcategoryService(SubcategoryRepository repository) {
        super(repository);
    }

    public SubcategoryEntity findByName(String name) {
        return repository.findByNameIgnoreCase(name)
                .orElseThrow(()-> new ResourceNotFoundException("Subcategory not found with name: " + name));
    }
}
