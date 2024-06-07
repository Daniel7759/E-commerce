package com.example.services;

import com.example.models.CategoryEntity;

import java.util.List;

public interface CategoryService {

    List<CategoryEntity> findAll();
    CategoryEntity findById(Long id);
    CategoryEntity findByName(String name);
    CategoryEntity insert(CategoryEntity category);
    CategoryEntity update(CategoryEntity category);
    void delete(Long id);

}
