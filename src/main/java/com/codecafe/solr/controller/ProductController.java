package com.codecafe.solr.controller;

import com.codecafe.solr.document.ProductDocument;
import com.codecafe.solr.persistence.entity.Product;
import com.codecafe.solr.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product savedProduct = productService.addProduct(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    // for debugging only
    @GetMapping("/db/products")
    ResponseEntity<List<Product>> listAllProductsFromDB() {
        List<Product> products = productService.fetchAllFromDB();
        return ResponseEntity.ok(products);
    }

    // for debugging only
    @GetMapping("/solr/products")
    ResponseEntity<List<ProductDocument>> listAllProductsFromSolr() {
        List<ProductDocument> products = productService.fetchAllFromSolr();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/name/{name}")
    ResponseEntity<List<ProductDocument>> findProductsByName(@PathVariable String name) {
        List<ProductDocument> products = productService.fetchAllProductsByName(name);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/description/{description}")
    ResponseEntity<List<ProductDocument>> findProductsByDescription(@PathVariable String description) {
        List<ProductDocument> products = productService.fetchAllProductsByDescription(description);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/search/{searchTerm}")
    ResponseEntity<List<ProductDocument>> findProductsBy(@PathVariable String searchTerm) {
        List<ProductDocument> products = productService.fetchAllProductsBy(searchTerm);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/facetedsearch/{productName}")
    ResponseEntity<FacetPage<ProductDocument>> findProductsBy(@PathVariable String productName, @RequestParam String facet) {
        FacetPage<ProductDocument> products = productService.fetchAllProductsByNameAndFacet(productName, facet, PageRequest.of(0, 20));
        return ResponseEntity.ok(products);
    }

}