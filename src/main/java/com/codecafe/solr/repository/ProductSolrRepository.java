package com.codecafe.solr.repository;

import com.codecafe.solr.document.ProductDocument;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.List;

public interface ProductSolrRepository extends SolrCrudRepository<ProductDocument, Long> {

    @Query("name:*?0*")
    List<ProductDocument> findByName(String name);

}