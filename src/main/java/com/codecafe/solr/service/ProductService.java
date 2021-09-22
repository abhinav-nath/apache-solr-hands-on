package com.codecafe.solr.service;

import com.codecafe.solr.document.ProductDocument;
import com.codecafe.solr.persistence.entity.Product;
import com.codecafe.solr.persistence.repository.ProductRepository;
import com.codecafe.solr.repository.ProductSolrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final SolrTemplate solrTemplate;
    private final ProductRepository productRepository;
    private final ProductSolrRepository productSolrRepository;

    @Autowired
    public ProductService(SolrTemplate solrTemplate, ProductRepository productRepository, ProductSolrRepository productSolrRepository) {
        this.solrTemplate = solrTemplate;
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

    public List<Product> fetchAllFromDB() {
        return productRepository.findAll();
    }

    public List<ProductDocument> fetchAllFromSolr() {
        return toProductDocuments(productSolrRepository.findAll());
    }

    public List<ProductDocument> fetchAllProductsByName(String name) {
        return productSolrRepository.findByName(name);
    }

    public List<ProductDocument> fetchAllProductsByDescription(String description) {
        return productSolrRepository.findByDescription(description);
    }

    public List<ProductDocument> fetchAllProductsBy(String searchTerm) {
        return productSolrRepository.findBy(searchTerm);
    }

    private List<ProductDocument> toProductDocuments(Iterable<ProductDocument> products) {
        List<ProductDocument> productDocuments = new ArrayList<>();
        products.forEach(productDocuments::add);
        return productDocuments;
    }

    public FacetPage<ProductDocument> fetchAllProductsByNameAndFacet(String productName, String facet, PageRequest pageRequest) {
        FacetQuery query = new SimpleFacetQuery(new Criteria("name").is(productName))
                .setFacetOptions(new FacetOptions(facet));
        query.setPageRequest(pageRequest);
        return solrTemplate.queryForFacetPage("productscatalog", query, ProductDocument.class);
    }

}