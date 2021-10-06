package com.codecafe.solr.controller;

import com.codecafe.solr.document.ProductDocument;
import com.codecafe.solr.model.CategoryView;
import com.codecafe.solr.model.ProductCategory;
import com.codecafe.solr.service.ProductSearchService;
import org.apache.solr.common.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products/search")
public class ProductSearchController {

    private final ProductSearchService productSearchService;

    @Autowired
    public ProductSearchController(ProductSearchService ProductSearchService) {
        this.productSearchService = ProductSearchService;
    }

    @GetMapping("/categories")
    ResponseEntity<List<CategoryView>> getAllProductCategories() {
        FacetPage<ProductDocument> products = productSearchService.fetchAllProductsByFacet("category", PageRequest.of(0, 20));
        return ResponseEntity.ok(productSearchService.getCategoryViewsFrom(products));
    }

    @GetMapping
    ResponseEntity<List<ProductDocument>> getAllProducts(@RequestParam(required = false) String category) {
        List<ProductDocument> products;
        if (StringUtils.isEmpty(category))
            products = productSearchService.fetchAllFromSolr();
        else
            products = productSearchService.fetchAllProductsByCategory(ProductCategory.valueOf(category.toUpperCase()));

        return ResponseEntity.ok(products);
    }

    @GetMapping("/name/{name}")
    ResponseEntity<List<ProductDocument>> findProductsByName(@PathVariable String name) {
        List<ProductDocument> products = productSearchService.fetchAllProductsByName(name);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/brand/{brand}")
    ResponseEntity<List<ProductDocument>> findProductsByBrand(@PathVariable String brand) {
        List<ProductDocument> products = productSearchService.fetchAllProductsByName(brand);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/description/{description}")
    ResponseEntity<List<ProductDocument>> findProductsByDescription(@PathVariable String description) {
        List<ProductDocument> products = productSearchService.fetchAllProductsByDescription(description);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{searchTerm}")
    ResponseEntity<List<ProductDocument>> findProductsBy(@PathVariable String searchTerm) {
        List<ProductDocument> products = productSearchService.fetchAllProductsBy(searchTerm);
        return ResponseEntity.ok(products);
    }

}