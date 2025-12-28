package com.example.sales.controller;

import com.example.sales.service.analytics.OptimizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/analytics/optimization")
public class OptimizationController {

    @Autowired
    private OptimizationService optimizationService;

    /**
     * Оптимизация распределения бюджета по продуктам
     */
    @GetMapping("/products")
    public void optimizeProductAllocation(@RequestParam BigDecimal budget) {
        optimizationService.optimizeProductAllocation(budget);
    }
}
