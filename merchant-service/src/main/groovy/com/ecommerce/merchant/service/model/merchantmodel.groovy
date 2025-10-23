package com.ecommerce.merchant.service.model


import jakarta.persistence.*

@Entity
@Table(name = "merchants")
class merchantmodel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    String name
    String email
    String password
    BigDecimal rating = 0.0

    @Column(name = "created_at", insertable = false, updatable = false)
    Date createdAt

    @Column(name = "updated_at", insertable = false, updatable = false)
    Date updatedAt
}


