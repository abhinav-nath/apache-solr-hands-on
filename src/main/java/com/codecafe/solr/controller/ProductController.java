package com.codecafe.solr.controller;

import com.codecafe.solr.document.ProductDocument;
import com.codecafe.solr.persistence.entity.Product;
import com.codecafe.solr.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/db/products")
    ResponseEntity<List<Product>> listAllProductsFromDB() {
        List<Product> products = productService.fetchAllFromDB();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/solr/products")
    ResponseEntity<List<ProductDocument>> listAllProductsFromSolr() {
        List<ProductDocument> products = productService.fetchAllFromSolr();
        return ResponseEntity.ok(products);
    }

}