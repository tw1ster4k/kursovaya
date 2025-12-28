package com.example.sales.service.analytics;

import com.example.sales.entity.Customer;
import com.example.sales.entity.Order;
import com.example.sales.entity.OrderItem;
import com.example.sales.entity.Sale;
import com.example.sales.repository.CustomerRepository;
import com.example.sales.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WeightedMetricsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SaleRepository saleRepository;

    /**
     * Метод взвешенных показателей эффективности клиентов
     * Весовые коэффициенты: сумма продаж, количество заказов
     */
    public Map<Long, BigDecimal> calculateCustomerEfficiency() {
        List<Customer> customers = customerRepository.findAll();

        return customers.stream()
                .collect(Collectors.toMap(
                        Customer::getCustomerId,
                        customer -> {
                            BigDecimal totalSales = saleRepository.findAll()
                                    .stream()
                                    .filter(sale -> sale.getOrder().getCustomer().getCustomerId().equals(customer.getCustomerId()))
                                    .map(Sale::getTotalAmount)
                                    .reduce(BigDecimal.ZERO, BigDecimal::add);

                            long ordersCount = customer.getOrders().size();

                            // Взвешенный показатель: 70% от суммы продаж + 30% от количества заказов
                            BigDecimal weight = totalSales.multiply(BigDecimal.valueOf(0.7))
                                    .add(BigDecimal.valueOf(ordersCount).multiply(BigDecimal.valueOf(0.3)));
                            return weight;
                        }
                ));
    }
}
