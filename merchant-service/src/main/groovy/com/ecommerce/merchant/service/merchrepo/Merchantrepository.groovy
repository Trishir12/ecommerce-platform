package com.ecommerce.merchant.service.merchrepo

import com.ecommerce.merchant.service.model.merchantmodel
import org.springframework.data.jpa.repository.JpaRepository

interface Merchantrepository extends JpaRepository<merchantmodel,Long>{
    Optional<merchantmodel> findByEmail(String email)
}


