package com.codecafe.solr.persistence.entity;

import com.codecafe.solr.document.ProductDocument;
import com.codecafe.solr.model.ProductCategory;
import com.codecafe.solr.model.ProductColor;
import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    @CsvBindByName(column = "date_added")
    private String dateAdded;

    @CsvBindByName(column = "in_stock")
    private boolean inStock;

    public Product() {
        if (dateAdded == null)
            dateAdded = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
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
                .inStock(inStock)
                .build();
    }

}