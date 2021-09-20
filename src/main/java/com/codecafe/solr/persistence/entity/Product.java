package com.codecafe.solr.persistence.entity;

import com.codecafe.solr.document.ProductDocument;
import com.codecafe.solr.model.ProductCategory;
import com.codecafe.solr.model.ProductColor;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CsvBindByName
    private String name;

    @Enumerated(EnumType.STRING)
    @CsvBindByName
    private ProductCategory category;

    @CsvBindByName
    private String brand;

    @CsvBindByName
    private String description;

    @Enumerated(EnumType.STRING)
    @CsvBindByName
    private ProductColor color;

    @CsvBindByName
    private Double price;

    @CsvDate(value = "yyyy-MM-dd")
    @CsvBindByName(column = "date_added")
    private LocalDate dateAdded;

    public Product() {
        if (dateAdded == null)
            LocalDate.now();
    }

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