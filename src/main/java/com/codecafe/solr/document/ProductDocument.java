package com.codecafe.solr.document;

import com.codecafe.solr.model.ProductCategory;
import com.codecafe.solr.model.ProductColor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SolrDocument(collection = "productscatalog")
public class ProductDocument {

    @Id
    private Long id;

    @Field
    private String name;

    @Field
    private ProductCategory category;

    @Field
    private String brand;

    @Field
    private String description;

    @Field
    private ProductColor color;

    @Field
    private String dateAdded;

}