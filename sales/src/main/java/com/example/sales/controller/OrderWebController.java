package com.example.sales.controller;

import com.example.sales.entity.Customer;
import com.example.sales.entity.Order;
import com.example.sales.service.OrderService;
import com.example.sales.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
public class OrderWebController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    // 1. Список всех заказов
    @GetMapping
    public String ordersPage(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "orders"; // Thymeleaf-шаблон orders.html
    }

    // 2. Форма добавления нового заказа
    @GetMapping("/add")
    public String addOrderForm(Model model) {
        Order order = new Order();
        order.setCustomer(new Customer());
        model.addAttribute("order", order);
        model.addAttribute("customers", customerService.getAllCustomers());
        return "order_form"; // Создадим шаблон order_form.html
    }

    // 3. Сохранение нового заказа
    @PostMapping("/add")
    public String addOrder(@ModelAttribute Order order) {
        orderService.createOrder(order);
        return "redirect:/orders";
    }

    // 4. Форма редактирования заказа
public String editOrderForm(@PathVariable Long id, Model model) {
    Order order = orderService.getOrderById(id);
    if (order.getCustomer() == null) {
        order.setCustomer(new Customer());
    }
    model.addAttribute("order", order);
    model.addAttribute("customers", customerService.getAllCustomers());
    return "order_form";
}

    // 5. Сохранение изменений заказа
    @PostMapping("/edit")
    public String editOrder(@ModelAttribute Order order) {
        orderService.updateOrder(order);
        return "redirect:/orders";
    }

    // 6. Удаление заказа
    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }
}
