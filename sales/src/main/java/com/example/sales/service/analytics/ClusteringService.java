package com.example.sales.service.analytics;

import com.example.sales.dto.CustomerClusterInfo;
import com.example.sales.entity.Customer;
import com.example.sales.entity.Sale;
import com.example.sales.repository.CustomerRepository;
import com.example.sales.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Простейшая кластеризация клиентов
 */
@Service
public class ClusteringService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SaleRepository saleRepository;

    /**
     * Разбиваем клиентов на 3 кластера по активности
     */
    public Map<Integer, List<CustomerClusterInfo>> clusterCustomers() {
    List<Customer> customers = customerRepository.findAll();

    Map<Long, CustomerClusterInfo> activityMap = new HashMap<>();

    for (Customer customer : customers) {
        BigDecimal totalSales = saleRepository.findAll()
                .stream()
                .filter(sale -> sale.getOrder().getCustomer().getCustomerId().equals(customer.getCustomerId()))
                .map(Sale::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int ordersCount = customer.getOrders().size();
        double activityScore = totalSales.doubleValue() * ordersCount;

        activityMap.put(customer.getCustomerId(),
                new CustomerClusterInfo(customer.getCustomerId(),
                        customer.getName(),
                        ordersCount,
                        activityScore));
    }

    // Определяем границы для кластеров
    double maxActivity = activityMap.values().stream()
            .mapToDouble(CustomerClusterInfo::getTotalActivity)
            .max()
            .orElse(1);

    double clusterStep = maxActivity / 3.0;

    Map<Integer, List<CustomerClusterInfo>> clusters = new HashMap<>();
    clusters.put(1, new ArrayList<>());
    clusters.put(2, new ArrayList<>());
    clusters.put(3, new ArrayList<>());

    for (CustomerClusterInfo info : activityMap.values()) {
        double value = info.getTotalActivity();
        if (value <= clusterStep) clusters.get(1).add(info);
        else if (value <= clusterStep * 2) clusters.get(2).add(info);
        else clusters.get(3).add(info);
    }

    return clusters;
}

}
