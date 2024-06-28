package com.example.models;

import com.example.repositories.Identifable;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity implements Serializable, Identifable<Long> {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String sku;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private LocalDate createdAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "marca_id", referencedColumnName = "marcaId", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private MarcaEntity marca;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subcategory_id", referencedColumnName = "subcategoryId", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private SubcategoryEntity subcategory;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ImageProductEntity> images;

    @JsonIgnore
    private Long views = 0L;

    @Override
    @JsonIgnore
    public Long getId() {
        return productId;
    }
}
