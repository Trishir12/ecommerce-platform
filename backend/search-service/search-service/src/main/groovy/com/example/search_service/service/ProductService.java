package com.example.search_service.service;

import com.example.search_service.model.Product;
import com.example.search_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> saveAll(List<Product> products) {
        return (List<Product>) productRepository.saveAll(products);
    }

    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Advanced search with optional filters, pagination, sorting
     */
    public Map<String, Object> searchProducts(String query,
                                              String category,
                                              Double minPrice,
                                              Double maxPrice,
                                              int page,
                                              int size,
                                              String sortBy,
                                              String sortOrder) {

        // Fetch all products matching query
        List<Product> allProducts = productRepository.findByNameContainingIgnoreCase(query != null ? query : "");

        // Filter by category
        if (category != null && !category.isEmpty()) {
            allProducts.removeIf(p -> !p.getCategory().equalsIgnoreCase(category));
        }

        // Filter by price
        if (minPrice != null) {
            allProducts.removeIf(p -> p.getPrice() < minPrice);
        }
        if (maxPrice != null) {
            allProducts.removeIf(p -> p.getPrice() > maxPrice);
        }

        // Sort
        allProducts.sort((p1, p2) -> {
            int result = 0;
            switch (sortBy) {
                case "name":
                    result = p1.getName().compareToIgnoreCase(p2.getName());
                    break;
                case "price":
                    result = Double.compare(p1.getPrice(), p2.getPrice());
                    break;
                default:
                    result = p1.getName().compareToIgnoreCase(p2.getName());
            }
            return sortOrder.equalsIgnoreCase("desc") ? -result : result;
        });

        // Pagination
        int start = page * size;
        int end = Math.min(start + size, allProducts.size());
        List<Product> pagedProducts = start < end ? allProducts.subList(start, end) : List.of();

        // Build response
        Map<String, Object> response = new HashMap<>();
        response.put("total", allProducts.size());
        response.put("page", page);
        response.put("size", size);
        response.put("products", pagedProducts);

        return response;
    }
}
