package com.ecommerce.merchant.service.merchrepo

import com.ecommerce.merchant.service.product.Merchantproducts
import org.springframework.data.jpa.repository.JpaRepository

interface Productrepo extends JpaRepository<Merchantproducts,Long>{

    List<Merchantproducts> findByMerchantId(Long merchantId)
}