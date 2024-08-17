package com.example.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "order_sales")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderSale {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_sale_id_seq")
    @SequenceGenerator(name = "order_sale_id_seq", sequenceName = "order_sale_id_seq", allocationSize = 1)
    private Long id;
    private LocalDateTime orderDate;
    private Long total;
    private String stripeSessionId;
    private String statusPayment = "PENDING";

    @OneToMany(mappedBy = "orderSale", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<DetailOrderSale> details;
}
