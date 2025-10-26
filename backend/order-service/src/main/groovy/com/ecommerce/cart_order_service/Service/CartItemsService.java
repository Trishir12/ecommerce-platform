package com.ecommerce.cart_order_service.Service;

import com.ecommerce.cart_order_service.Model.CartItems;
import com.ecommerce.cart_order_service.Repository.CartItemsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemsService {
    @Autowired
    private final CartItemsRepository cartRepo;

    public CartItemsService(CartItemsRepository cartRepo) {
        this.cartRepo = cartRepo;
    }

    public void addToCart(CartItems item) {
        Optional<CartItems> existing = cartRepo
                .findByUserIdAndProductIdAndMerchantId(item.getUserId(), item.getProductId(), item.getMerchantId());

        if (existing.isPresent()) {
            CartItems cartItem = existing.get();
            cartItem.setQuantity(cartItem.getQuantity() + item.getQuantity());
            cartItem.setPrice(item.getPrice());
            cartRepo.save(cartItem);
        } else {
            cartRepo.save(item); // make sure item.price is set from frontend
        }
    }


    public List<CartItems> getCartByUser(Long userId) {
        return cartRepo.findByUserId(userId);
    }


    @Transactional
    public void updateCart(CartItems item) {
        Optional<CartItems> existing = cartRepo
                .findByUserIdAndProductIdAndMerchantId(
                        item.getUserId(),
                        item.getProductId(),
                        item.getMerchantId()
                );

        if (existing.isPresent()) {
            CartItems cartItem = existing.get();
            cartItem.setQuantity(item.getQuantity());
            cartItem.setPrice(item.getPrice());
            cartRepo.save(cartItem);
        } else {
            cartRepo.save(item);
        }
    }

    @Transactional
    public void removeFromCart(Long userId, String productId, Long merchantId) {
        cartRepo.deleteByUserIdAndProductIdAndMerchantId(userId, productId, merchantId);
    }
}
