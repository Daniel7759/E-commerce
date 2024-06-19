package com.example.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "marcas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MarcaEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long marcaId;
    private String name;
    private String country;

    @Column(name = "logo", columnDefinition = "TEXT")
    private String logo;

    @OneToMany(mappedBy = "marca", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ProductEntity> products;

}
