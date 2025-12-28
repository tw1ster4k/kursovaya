package com.example.sales.controller;

import com.example.sales.entity.Product;
import com.example.sales.entity.Sale;
import com.example.sales.service.ProductService;
import com.example.sales.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProductFilterController {

    @Autowired
    private ProductService productService;

    @Autowired
    private SaleService saleService;

    @GetMapping("/sales-web/filter-by-product")
    public String filterSalesByProduct(
            @RequestParam(required = false) Long productId,
            Model model) {

        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);

        List<Sale> filteredSales = saleService.getAllSales();

        if (productId != null) {
            filteredSales = filteredSales.stream()
                    .filter(sale -> sale.getOrder().getOrderItems().stream()
                            .anyMatch(item -> item.getProduct().getId().equals(productId)))
                    .collect(Collectors.toList());
        }

        model.addAttribute("sales", filteredSales);
        model.addAttribute("selectedProductId", productId);

        return "sales/filter_by_product"; // новый шаблон
    }
}
