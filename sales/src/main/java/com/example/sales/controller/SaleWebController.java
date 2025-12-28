package com.example.sales.controller;

import com.example.sales.entity.Sale;
import com.example.sales.entity.Order;
import com.example.sales.service.OrderService;
import com.example.sales.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sales-web")
public class SaleWebController {

    @Autowired
    private SaleService saleService;

    @Autowired
    private OrderService orderService;

    // 1. Страница со всеми продажами
    @GetMapping
    public String salesPage(Model model) {
        model.addAttribute("sales", saleService.getAllSales());
        return "sales"; // sales.html
    }

    // 2. Форма для добавления продажи
    @GetMapping("/add")
    public String addSaleForm(Model model) {
        model.addAttribute("sale", new Sale());
        model.addAttribute("orders", orderService.getAllOrders()); // <- обязательно
        return "sale_form";
    }

    // 3. Обработка добавления продажи
    @PostMapping("/add")
    public String addSale(@ModelAttribute Sale sale, @RequestParam("order") Long orderId) {
        Order order = orderService.getOrderById(orderId);
        sale.setOrder(order);
        saleService.saveSale(sale);
        System.out.println(sale);
        System.out.println(order);
        return "redirect:/sales-web/";
    }

    // 4. Форма для редактирования продажи
    @GetMapping("/edit/{id}")
    public String editSaleForm(@PathVariable Long id, Model model) {
        model.addAttribute("sale", saleService.getSaleById(id));
        model.addAttribute("orders", orderService.getAllOrders());
        return "sale_form";
    }
    // 5. Обработка редактирования продажи
    @PostMapping("/edit")
    public String editSale(@ModelAttribute Sale sale, @RequestParam("order") Long orderId) {
        Order order = orderService.getOrderById(orderId);
        sale.setOrder(order);
        saleService.updateSale(sale);
        return "redirect:/sales-web";
    }


    // 6. Удаление продажи
    @GetMapping("/delete/{id}")
    public String deleteSale(@PathVariable Long id) {
        saleService.deleteSale(id);
        return "redirect:/sales-web";
    }
    
}
