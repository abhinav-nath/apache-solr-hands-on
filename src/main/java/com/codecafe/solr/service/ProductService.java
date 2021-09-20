package com.codecafe.solr.service;

import com.codecafe.solr.document.ProductDocument;
import com.codecafe.solr.persistence.entity.Product;
import com.codecafe.solr.persistence.repository.ProductRepository;
import com.codecafe.solr.repository.ProductSolrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductSolrRepository productSolrRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductSolrRepository productSolrRepository) {
        this.productRepository = productRepository;
        this.productSolrRepository = productSolrRepository;
    }

    @Transactional
    public Product addProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        ProductDocument productDocument = savedProduct.toProductDocument();
        productSolrRepository.save(productDocument);
        return savedProduct;
    }

}