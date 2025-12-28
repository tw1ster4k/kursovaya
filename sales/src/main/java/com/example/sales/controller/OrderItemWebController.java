package com.example.sales.controller;

import com.example.sales.entity.OrderItem;
import com.example.sales.entity.Order;
import com.example.sales.service.OrderItemService;
import com.example.sales.service.OrderService;
import com.example.sales.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/order-items")
public class OrderItemWebController {

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public String orderItemsPage(Model model) {
        model.addAttribute("orderItems", orderItemService.getAllOrderItems());
        return "order_items";
    }

    @GetMapping("/add")
    public String addOrderItemForm(Model model) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(new Order()); // ⚡ важно, иначе order будет null
        model.addAttribute("orderItem", orderItem);
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("products", productService.getAllProducts());
        return "order_item_form";
    }

    @GetMapping("/edit/{id}")
    public String editOrderItemForm(@PathVariable Long id, Model model) {
        model.addAttribute("orderItem", orderItemService.getOrderItemById(id));
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("products", productService.getAllProducts());
        return "order_item_form";
    }

    @PostMapping("/add")
    public String addOrderItem(@ModelAttribute OrderItem orderItem) {
        Long orderId = orderItem.getOrder().getId();
        Order order = orderService.getOrderById(orderId);
        orderItem.setOrder(order);

        orderItemService.saveOrderItem(orderItem);
        return "redirect:/order-items";
    }

    @PostMapping("/edit")
    public String editOrderItem(@ModelAttribute OrderItem orderItem) {
    Long orderId = orderItem.getOrder().getId();
    Order order = orderService.getOrderById(orderId);
    orderItem.setOrder(order);

    orderItemService.updateOrderItem(orderItem.getId(), orderItem);
    return "redirect:/order-items";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrderItem(@PathVariable Long id) {
        orderItemService.deleteOrderItem(id);
        return "redirect:/order-items";
    }
}
