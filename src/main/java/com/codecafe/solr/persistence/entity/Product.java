package com.codecafe.solr.persistence.entity;

import com.codecafe.solr.document.ProductDocument;
import com.codecafe.solr.model.ProductCategory;
import com.codecafe.solr.model.ProductColor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    private String brand;
    private String description;

    @Enumerated(EnumType.STRING)
    private ProductColor color;

    private final LocalDate dateAdded = LocalDate.now();

    public ProductDocument toProductDocument() {
        return ProductDocument.builder()
                .id(id)
                .name(name)
                .category(category)
                .brand(brand)
                .description(description)
                .color(color)
                .dateAdded(dateAdded)
                .build();
    }

}