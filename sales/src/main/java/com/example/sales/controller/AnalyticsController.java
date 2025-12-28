package com.example.sales.controller;

import com.example.sales.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    // Страница аналитики (главная)
    @GetMapping
    public String analyticsHome() {
        return "analytics/home"; // шаблон: templates/analytics/home.html
    }

    // 1. Общая сумма продаж
    @GetMapping("/total-sales")
    public String totalSalesPage(Model model) {
        model.addAttribute("totalSales", analyticsService.getTotalSalesAmount());
        return "analytics/total-sales"; // шаблон: templates/analytics/total-sales.html
    }

    // 2. Количество заказов по каждому клиенту
    @GetMapping("/orders-count")
    public String ordersCountPage(Model model) {
        model.addAttribute("ordersCount", analyticsService.getOrdersCountByCustomer());
        return "analytics/orders-count"; // шаблон: templates/analytics/orders-count.html
    }

    // 3. Средний чек продажи
    @GetMapping("/average-sale")
    public String averageSalePage(Model model) {
        model.addAttribute("averageSale", analyticsService.getAverageSaleAmount());
        return "analytics/average-sale"; // шаблон: templates/analytics/average-sale.html
    }

    // 4. Топ-N продуктов по количеству продаж
    @GetMapping("/top-products")
    public String topProductsPage(@RequestParam(defaultValue = "5") int topN, Model model) {
        List<Long> topProducts = analyticsService.getTopProductsBySales(topN);
        model.addAttribute("topProducts", topProducts);
        return "analytics/top-products"; // шаблон: templates/analytics/top-products.html
    }

    // 5. Эффективность клиентов (общая сумма заказов на клиента)
    @GetMapping("/customer-efficiency")
    public String customerEfficiencyPage(Model model) {
        Map<Long, BigDecimal> efficiency = analyticsService.getCustomerEfficiency();
        model.addAttribute("customerEfficiency", efficiency);
        return "analytics/customer-efficiency"; // шаблон: templates/analytics/customer-efficiency.html
    }
}
