package com.ecommerce.cart_order_service.Service;

import com.ecommerce.cart_order_service.Model.Order;
import com.ecommerce.cart_order_service.Repository.CartItemsRepository;
import com.ecommerce.cart_order_service.Repository.OrderRepository;
import com.ecommerce.cart_order_service.util.NotificationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepo;
    private final CartItemsRepository cartRepo;
    private final RestTemplate restTemplate;
    private final NotificationUtil notificationUtil;

    public Order placeOrder(Order order) {
        Order savedOrder = orderRepo.save(order);

        cartRepo.deleteByUserId(order.getUserId());
        System.out.println("✅ Cleared cart for user ID: " + order.getUserId());

        order.getItems().forEach(item -> {
            try {
                Map<String, Object> stockUpdate = Map.of(
                        "productId", item.getProductId(),
                        "merchantId", item.getMerchantId(),
                        "quantity", item.getQuantity()
                );
                restTemplate.postForEntity(
                        "http://localhost:8095/products/updateStock",
                        stockUpdate,
                        Void.class
                );
                System.out.println("Stock update sent for product " + item.getProductId());
            } catch (Exception e) {
                System.err.println("⚠Could not update stock for product " + item.getProductId() + ": " + e.getMessage());
            }
        });

        // Step 4️⃣: Send user notification
        notificationUtil.sendOrderConfirmation(order.getUserId(), savedOrder.getId(), savedOrder.getTotalAmount());

        // Step 5️⃣: Return response
        return savedOrder;
    }

    // Get all orders by user
    public List<Order> getOrdersByUser(Long userId) {
        return orderRepo.findByUserId(userId);
    }
}
