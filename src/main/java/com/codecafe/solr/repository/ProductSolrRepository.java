package com.codecafe.solr.repository;

import com.codecafe.solr.document.ProductDocument;
import org.springframework.data.solr.repository.SolrCrudRepository;

public interface ProductSolrRepository extends SolrCrudRepository<ProductDocument, Long> {
}