package com.example.services;

import com.example.exceptions.ResourceNotFoundException;
import com.example.models.CategoryEntity;
import com.example.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryEntity> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public CategoryEntity findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category not found with id: " + id));
    }

    @Override
    public CategoryEntity findByName(String name) {
        return categoryRepository.findByNameIgnoreCase(name)
                .orElseThrow(()-> new ResourceNotFoundException("Category not found with name: " + name));
    }

    @Override
    public CategoryEntity insert(CategoryEntity category) {
        return categoryRepository.save(category);
    }

    @Override
    public CategoryEntity update(CategoryEntity category) {
        if (!categoryRepository.existsById(category.getCategoryId())) {
            throw new ResourceNotFoundException("Category not found with id: " + category.getCategoryId());
        }
        return categoryRepository.save(category);
    }

    @Override
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }
}
