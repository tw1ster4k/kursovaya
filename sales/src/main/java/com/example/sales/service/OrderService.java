package com.example.sales.service;

import com.example.sales.entity.Order;
import java.util.List;

public interface OrderService {

    Order createOrder(Order order); // Создать заказ с товарами

    Order updateOrder(Order order); //Обновить существующий заказ

    List<Order> getAllOrders(); // Получить все заказы

    Order getOrderById(Long orderId); // Получить заказ по ID

    List<Order> getOrdersByCustomerId(Long customerId); // Заказы конкретного клиента

    void deleteOrder(Long orderId); // Удалить заказ
}
