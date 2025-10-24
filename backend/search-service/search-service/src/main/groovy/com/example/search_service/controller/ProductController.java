package com.example.search_service.controller;

import com.example.search_service.model.Product;
import com.example.search_service.service.ProductService;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public Product addProduct(@Valid @RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @PostMapping("/addAll")
    public List<Product> addAllProducts(@Valid @RequestBody List<Product> products) {
        return productService.saveAll(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable String id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Iterable<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/category/{category}")
    public List<Product> getProductsByCategory(@PathVariable String category) {
        return productService.getProductsByCategory(category);
    }

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String q) {
        return productService.searchProductsByName(q);
    }

    @PostMapping("/advanced-search")
    public ResponseEntity<Map<String, Object>> advancedSearch(@Valid @RequestBody AdvancedSearchRequest request) {
        Map<String, Object> results = productService.searchProducts(
                request.getQuery(),
                request.getCategory(),
                request.getMinPrice(),
                request.getMaxPrice(),
                request.getPage(),
                request.getSize(),
                request.getSortBy(),
                request.getSortOrder()
        );
        return ResponseEntity.ok(results);
    }

    @Data
    public static class AdvancedSearchRequest {
        private String query;
        private String category;
        private Double minPrice;
        private Double maxPrice;
        private int page = 0;
        private int size = 10;
        private String sortBy = "name";
        private String sortOrder = "asc";
    }
}
