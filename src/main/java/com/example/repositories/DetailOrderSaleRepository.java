package com.example.repositories;

import com.example.models.DetailOrderSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailOrderSaleRepository extends JpaRepository<DetailOrderSale, Long> {
}

