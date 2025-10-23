package com.ecommerce.merchant.service.product
import jakarta.persistence.*

@Entity
@Table(name = "merchant_products")
class Merchantproducts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    Long merchantId
    String productId
    BigDecimal price
    Integer stock
}

