package com.codecafe.solr.service;

import com.codecafe.solr.document.ProductDocument;
import com.codecafe.solr.model.CategoryView;
import com.codecafe.solr.model.ProductCategory;
import com.codecafe.solr.persistence.repository.ProductRepository;
import com.codecafe.solr.repository.ProductSolrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.FacetOptions;
import org.springframework.data.solr.core.query.FacetQuery;
import org.springframework.data.solr.core.query.SimpleFacetQuery;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductSearchService {

    private final SolrTemplate solrTemplate;
    private final ProductRepository productRepository;
    private final ProductSolrRepository productSolrRepository;

    @Autowired
    public ProductSearchService(SolrTemplate solrTemplate, ProductRepository productRepository, ProductSolrRepository productSolrRepository) {
        this.solrTemplate = solrTemplate;
        this.productRepository = productRepository;
        this.productSolrRepository = productSolrRepository;
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

    public List<ProductDocument> fetchProductsByCategory(String searchTerm, String category) {
        return productSolrRepository.findBy(searchTerm);
    }

    public List<ProductDocument> fetchAllProductsByCategory(ProductCategory category) {
        return productSolrRepository.findByCategory(category);
    }

    public List<ProductDocument> fetchAllProductsBy(String searchTerm) {
        return productSolrRepository.findBy(searchTerm);
    }

    public FacetPage<ProductDocument> fetchAllProductsByFacet(String facet, PageRequest pageRequest) {
        FacetQuery query = new SimpleFacetQuery(new Criteria("name").isNotNull())
                .setFacetOptions(new FacetOptions(facet));
        query.setPageRequest(pageRequest);
        return solrTemplate.queryForFacetPage("productscatalog", query, ProductDocument.class);
    }

    public List<CategoryView> getCategoryViewsFrom(FacetPage<ProductDocument> products) {
        return products.getFacetResultPage("category").getContent().stream().map(
                        c -> CategoryView.builder()
                                .category(c.getValue())
                                .count(c.getValueCount())
                                .build())
                .collect(Collectors.toList());
    }

    private List<ProductDocument> toProductDocuments(Iterable<ProductDocument> products) {
        List<ProductDocument> productDocuments = new ArrayList<>();
        products.forEach(productDocuments::add);
        return productDocuments;
    }

}