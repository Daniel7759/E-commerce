package com.example.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "detail_order_sales")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DetailOrderSale {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "detail_order_sale_id_seq")
    @SequenceGenerator(name = "detail_order_sale_id_seq", sequenceName = "detail_order_sale_id_seq", allocationSize = 1)
    private Long id;

    private Long productId;
    private Long quantity;
    private Long unitPrice;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, targetEntity = OrderSale.class)
    @JoinColumn(name = "order_sale_id", referencedColumnName = "id")
    private OrderSale orderSale;
}
