package com.example.repositories;

import com.example.models.SubcategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubcategoryRepository extends JpaRepository<SubcategoryEntity, Long> {
    Optional<SubcategoryEntity> findByNameIgnoreCase(String name);
}
