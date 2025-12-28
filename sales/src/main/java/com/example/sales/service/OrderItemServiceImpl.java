package com.example.sales.service;

import com.example.sales.entity.OrderItem;
import com.example.sales.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public OrderItem saveOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    @Override
    public OrderItem getOrderItemById(Long id) {
        return orderItemRepository.findById(id).orElse(null);
    }

    @Override
    public OrderItem updateOrderItem(Long id, OrderItem orderItem) {
        OrderItem existing = orderItemRepository.findById(id).orElse(null);
        if (existing != null) {
            if (Objects.nonNull(orderItem.getQuantity())) {
                existing.setQuantity(orderItem.getQuantity());
            }
            if (Objects.nonNull(orderItem.getProduct())) {
                existing.setProduct(orderItem.getProduct());
            }
            if (Objects.nonNull(orderItem.getOrder())) {
                existing.setOrder(orderItem.getOrder());
            }
            return orderItemRepository.save(existing);
        }
        return null;
    }

    @Override
    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }
}
