package com.example.demo.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class DiscountContext {

    private final Map<String, DiscountStrategy> strategies;

    @Autowired
    public DiscountContext(Map<String, DiscountStrategy> strategies) {
        this.strategies = strategies;
    }

    public double calculateFinalPrice(String discountType, double originalPrice) {
        if (discountType == null) {
            return originalPrice;
        }
        DiscountStrategy strategy = strategies.getOrDefault(discountType.toUpperCase(), strategies.get("NONE"));
        return strategy.applyDiscount(originalPrice);
    }
}