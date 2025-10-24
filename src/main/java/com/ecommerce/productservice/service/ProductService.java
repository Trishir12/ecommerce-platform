package com.ecommerce.productservice.service;

import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile; // <-- ADDED

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FileStorageService fileStorageService;


    public Product createProduct(Product product, MultipartFile imageFile) {

        String imageUrl = fileStorageService.store(imageFile);


        product.setImageUrl(imageUrl);


        return productRepository.save(product);
    }

    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }


    public Optional<Product> updateProduct(String id, Product productDetails) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    existingProduct.setName(productDetails.getName());
                    existingProduct.setCategory(productDetails.getCategory());
                    existingProduct.setBrand(productDetails.getBrand());
                    existingProduct.setDescription(productDetails.getDescription());
                    existingProduct.setAttributes(productDetails.getAttributes());
                    existingProduct.setMerchants(productDetails.getMerchants());


                    if (productDetails.getImageUrl() != null) {
                        existingProduct.setImageUrl(productDetails.getImageUrl());
                    }

                    return productRepository.save(existingProduct);
                });
    }


    public boolean deleteProduct(String id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}