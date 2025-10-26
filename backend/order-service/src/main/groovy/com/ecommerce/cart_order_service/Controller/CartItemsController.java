package com.ecommerce.cart_order_service.Controller;

import com.ecommerce.cart_order_service.Model.CartItems;
import com.ecommerce.cart_order_service.Service.CartItemsService;
import com.ecommerce.cart_order_service.util.AuthValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "http://localhost:5173")
public class CartItemsController {

    @Autowired
    private CartItemsService cartService;

    @Autowired
    private AuthValidator authValidator;

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @Valid @RequestBody CartItems item) {

        Map<String, Object> claims = authValidator.validateToken(authHeader);
        if (!"END_USER".equalsIgnoreCase((String) claims.get("role"))) {
            return ResponseEntity.status(403).body(Map.of("error", "Only END_USER can add to cart."));
        }

        cartService.addToCart(item);
        return ResponseEntity.ok(Map.of("success", true));
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<List<CartItems>> getCart(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @PathVariable("userId") Long userId) {

        authValidator.validateToken(authHeader); // token must be valid
        return ResponseEntity.ok(cartService.getCartByUser(userId));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCart(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @Valid @RequestBody CartItems item) {

        authValidator.validateToken(authHeader);
        cartService.updateCart(item);
        return ResponseEntity.ok(Map.of("success", true));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeFromCart(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @Valid @RequestBody CartItems item) {

        authValidator.validateToken(authHeader);
        cartService.removeFromCart(item.getUserId(), item.getProductId(), item.getMerchantId());
        return ResponseEntity.ok(Map.of("success", true));
    }
}
