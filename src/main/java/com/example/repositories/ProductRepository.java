package com.example.repositories;

import com.example.models.ProductEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long>{

    Optional<ProductEntity> findByName(String name);
    List<ProductEntity> findBySubcategorySubcategoryId(Long subcategoryId);
    List<ProductEntity> findByMarcaMarcaId(Long marcaId);
    List<ProductEntity> findBySubcategoryName(String subcategoryName);

    @Query("SELECT p FROM ProductEntity p WHERE p.views IS NOT NULL ORDER BY p.views DESC")
    List<ProductEntity> findTop5ByViewsNotNullOrderByViewsDesc(Pageable pageable);

}
