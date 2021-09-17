package com.codecafe.solr.persistence.entity;

import com.codecafe.solr.model.ProductCategory;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@Getter
@MappedSuperclass
public class AbstractProduct {

    private String name;

    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    private String brand;
    private String description;
    private LocalDate dateAdded = LocalDate.now();

}