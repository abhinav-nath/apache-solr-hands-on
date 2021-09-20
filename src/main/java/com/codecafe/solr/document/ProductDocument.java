package com.codecafe.solr.document;

import com.codecafe.solr.model.ProductCategory;
import com.codecafe.solr.model.ProductColor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SolrDocument(collection = "productscatalog")
public class ProductDocument {

    @Id
    @Indexed
    private Long id;

    @Indexed
    private String name;

    @Indexed
    private ProductCategory category;

    @Indexed
    private String brand;

    @Indexed
    private String description;

    @Indexed
    private ProductColor color;

    @Indexed
    private String dateAdded;

}