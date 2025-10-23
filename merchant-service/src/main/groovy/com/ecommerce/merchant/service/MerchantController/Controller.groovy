//package com.ecommerce.Merchantservice.service

import com.ecommerce.merchant.service.model.merchantmodel
import com.ecommerce.merchant.service.service
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/merchants")
public class Controller {
    private final service merchantService;
    @Autowired
    public Controller(service merchantService) {
        this.merchantService = merchantService
    }

    @GetMapping("/{id}")
    merchantmodel getMerchant(@PathVariable Long id) {
        return merchantService.getMerchantById(id)
    }

    @PutMapping("/{id}")
    merchantmodel updateMerchant(@PathVariable Long id, @RequestBody merchantmodel merchant) {
        return merchantService.updateMerchant(id, merchant)
    }

    @PostMapping("/register")
    merchantmodel createMerchant(@RequestBody merchantmodel merchant) {
        return merchantService.createMerchant(merchant)
    }

}
