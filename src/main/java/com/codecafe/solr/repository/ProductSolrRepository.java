package com.codecafe.solr.repository;

import com.codecafe.solr.document.ProductDocument;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.List;

public interface ProductSolrRepository extends SolrCrudRepository<ProductDocument, Long> {

    @Query("name:*?0*")
    List<ProductDocument> findByName(String name);

    @Query("description:*?0*")
    List<ProductDocument> findByDescription(String description);

    @Query("category:*?0*")
    List<ProductDocument> findByCategory(String category);

    @Query("name:*?0* OR category:*?0* OR description:*?0* OR brand:*?0*")
    List<ProductDocument> findBy(String searchTerm);

}