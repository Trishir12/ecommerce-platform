package com.ecommerce.cart_order_service.util;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class AuthValidator {

    private static final String USER_SERVICE_URL = "http://localhost:8092/auth/validate";

    private final RestTemplate restTemplate = new RestTemplate();

    public Map<String, Object> validateToken(String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new RuntimeException("Missing or invalid Authorization header");
            }

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authHeader);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    USER_SERVICE_URL, HttpMethod.GET, entity, Map.class
            );
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Unauthorized: Invalid or expired token");
        }
    }
}
