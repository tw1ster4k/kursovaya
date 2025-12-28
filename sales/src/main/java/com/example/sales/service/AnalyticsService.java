package com.example.sales.service;

import com.example.sales.entity.Order;
import com.example.sales.entity.Sale;
import com.example.sales.repository.OrderRepository;
import com.example.sales.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private OrderRepository orderRepository;

    // 1. Общая сумма продаж
    public double getTotalSalesAmount() {
        return saleRepository.findAll()
                .stream()
                .mapToDouble(sale -> sale.getTotalAmount().doubleValue())
                .sum();
    }

    // 2. Количество заказов по каждому клиенту
    public Map<Long, Long> getOrdersCountByCustomer() {
        return orderRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(
                        o -> o.getCustomer().getCustomerId(),
                        Collectors.counting()
                ));
    }

    // 3. Средний чек продажи
    public double getAverageSaleAmount() {
        return saleRepository.findAll()
                .stream()
                .mapToDouble(sale -> sale.getTotalAmount().doubleValue())
                .average()
                .orElse(0.0);
    }

    // 4. Топ-5 продуктов по количеству продаж
public List<Long> getTopProductsBySales(int topN) {
    return saleRepository.findAll()
            .stream()
            .flatMap(sale -> sale.getOrder().getOrderItems().stream())
            .collect(Collectors.groupingBy(
                    item -> item.getProduct().getId(), // если Product.id
                    Collectors.summingInt(item -> item.getQuantity())
            ))
            .entrySet()
            .stream()
            .sorted(Map.Entry.<Long, Integer>comparingByValue().reversed())
            .limit(topN)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
}

// внутри класса AnalyticsService

// Метод взвешенных показателей: например, эффективность клиентов по сумме и количеству заказов
public Map<Long, BigDecimal> getCustomerEfficiency() {
        return orderRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(
                        order -> order.getCustomer().getCustomerId(),
                        Collectors.mapping(
                                order -> order.getOrderItems()
                                        .stream()
                                        .map(item -> item.getProduct().getPrice()
                                                .multiply(BigDecimal.valueOf(item.getQuantity())))
                                        .reduce(BigDecimal.ZERO, BigDecimal::add),
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                        )
                ));
    }

}
