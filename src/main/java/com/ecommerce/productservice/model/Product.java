package com.ecommerce.productservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@Document(collection = "productsNew")
public class Product {

    @Id
    private String id;
    private String name;
    private String category;
    private String brand;
    private String description;
    private Attributes attributes;
    private List<Merchant> merchants;
    private String imageUrl; // <-- ADDED THIS

    @Data
    public static class Attributes {
        private String storage;
        private String color;
    }

    @Data
    public static class Merchant {
        private int merchant_id;
        private String name;
        private double price;
        private int stock;
        private double rating;
    }
}