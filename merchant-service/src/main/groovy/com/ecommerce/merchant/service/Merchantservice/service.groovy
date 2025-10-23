package com.ecommerce.merchant.service;

//import com.ecommerce.merchant.service.merchrepo.MerchantRepository
import com.ecommerce.merchant.service.merchrepo.Merchantrepository;
//import com.ecommerce.merchant.service.model.MerchantModel
import com.ecommerce.merchant.service.model.merchantmodel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class service {

    private final Merchantrepository merchantRepository;

    @Autowired
    public service(Merchantrepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    public merchantmodel getMerchantById(Long id) {
        Optional<merchantmodel> merchant = merchantRepository.findById(id);
        return merchant.orElse(null);
    }

    public merchantmodel updateMerchant(Long id, merchantmodel updatedData) {
         merchantmodel merchant = merchantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Merchant not found with id " + id));

        merchant.setName(updatedData.getName());
        merchant.setPassword(updatedData.getPassword());

        return merchantRepository.save(merchant);
    }

    public merchantmodel createMerchant(merchantmodel merchant) {
        return merchantRepository.save(merchant);
    }
}
