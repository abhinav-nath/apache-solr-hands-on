package com.codecafe.solr;

import com.codecafe.solr.persistence.entity.Product;
import com.codecafe.solr.persistence.repository.ProductRepository;
import com.codecafe.solr.repository.ProductSolrRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private final ProductRepository productRepository;
    private final ProductSolrRepository productSolrRepository;

    public DataLoader(ProductRepository productRepository, ProductSolrRepository productSolrRepository) {
        this.productRepository = productRepository;
        this.productSolrRepository = productSolrRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Product> products = parseCSV();
        performRepositoryOperations(products);
    }

    private List<Product> parseCSV() throws FileNotFoundException {
        Reader reader = new BufferedReader(new FileReader("src/main/resources/simple_product_catalog.csv"));

        // create csv bean reader
        CsvToBean<Product> csvToBean = new CsvToBeanBuilder(reader)
                .withType(Product.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

        // convert CsvToBean object to list of products
        return csvToBean.parse();
    }

    @Transactional
    private void performRepositoryOperations(List<Product> products) {
        productSolrRepository.deleteAll();
        productRepository.saveAll(products);
        productSolrRepository.saveAll(products.stream().map(Product::toProductDocument).collect(Collectors.toList()));
    }

}