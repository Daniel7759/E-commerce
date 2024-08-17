package com.example.repositories;

import com.example.models.OrderSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderSaleRepository extends JpaRepository<OrderSale, Long> {
    OrderSale findByStripeSessionId(String stripeSessionId);
}
