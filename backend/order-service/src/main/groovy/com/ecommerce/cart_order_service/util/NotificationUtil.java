package com.ecommerce.cart_order_service.util;

import org.springframework.stereotype.Component;

@Component
public class NotificationUtil {

    public void sendOrderConfirmation(Long userId, Long orderId, Double totalAmount) {
        // Mock logic (replace with email/SMS API in future)
        System.out.println("📧 Sending order confirmation:");
        System.out.println("User ID: " + userId);
        System.out.println("Order ID: " + orderId);
        System.out.println("Total: ₹" + totalAmount);
        System.out.println("Message: Your order has been placed successfully!");
    }
}
