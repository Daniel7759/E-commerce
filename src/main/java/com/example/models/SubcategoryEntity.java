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
import java.util.List;

@Entity
@Table(name = "subcategories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubcategoryEntity implements Serializable, Identifable<Long> {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subcategoryId;
    private String name;
    private String image;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", referencedColumnName = "categoryId", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private CategoryEntity category;


    @Override
    @JsonIgnore
    public Long getId() {
        return subcategoryId;
    }
}
