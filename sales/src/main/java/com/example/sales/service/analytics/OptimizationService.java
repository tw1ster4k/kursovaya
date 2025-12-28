package com.example.sales.service.analytics;

import com.example.sales.entity.Product;
import com.example.sales.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Оптимизация запасов или прибыли с помощью простого линейного программирования
 */
@Service
public class OptimizationService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Находим оптимальное распределение продаж для максимизации прибыли
     */
    public void optimizeProductAllocation(BigDecimal budget) {
        List<Product> products = productRepository.findAll();

        // Простейшая логика: выбираем товары с максимальной рентабельностью
        products.sort((p1, p2) -> p2.getPrice().compareTo(p1.getPrice()));

        BigDecimal remainingBudget = budget;

        System.out.println("Рекомендации по распределению бюджета:");
        for (Product product : products) {
            if (remainingBudget.compareTo(product.getPrice()) >= 0) {
                long units = remainingBudget.divide(product.getPrice(), BigDecimal.ROUND_DOWN).longValue();
                remainingBudget = remainingBudget.subtract(product.getPrice().multiply(BigDecimal.valueOf(units)));
                System.out.println("Товар: " + product.getName() + ", Кол-во: " + units);
            }
        }
    }
}
