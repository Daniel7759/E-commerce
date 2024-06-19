package com.example.repositories;

import com.example.models.ImageProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageProductRepository extends JpaRepository<ImageProductEntity, Long> {

    List<ImageProductEntity> findByProductProductId(Long productId);
}
