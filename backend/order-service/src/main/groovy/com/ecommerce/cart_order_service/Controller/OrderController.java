package com.ecommerce.cart_order_service.Controller;

import com.ecommerce.cart_order_service.Model.Order;
import com.ecommerce.cart_order_service.Service.OrderService;
import com.ecommerce.cart_order_service.util.AuthValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private AuthValidator authValidator;

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @Valid @RequestBody Order order) {

        Map<String, Object> claims = authValidator.validateToken(authHeader);
        if (!"END_USER".equalsIgnoreCase((String) claims.get("role"))) {
            return ResponseEntity.status(403).body(Map.of("error", "Only END_USER can place orders."));
        }

        Order savedOrder = orderService.placeOrder(order);
        return ResponseEntity.ok(Map.of("success", true, "order_id", savedOrder.getId()));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Order>> getOrders(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @PathVariable("userId") Long userId) {

        authValidator.validateToken(authHeader);
        return ResponseEntity.ok(orderService.getOrdersByUser(userId));
    }
}
