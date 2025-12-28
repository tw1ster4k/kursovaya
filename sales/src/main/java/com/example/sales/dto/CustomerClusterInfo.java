package com.example.sales.dto;

public class CustomerClusterInfo {
    private Long customerId;
    private String name;
    private int ordersCount;
    private double totalActivity; // сумма продаж * кол-во заказов

    public CustomerClusterInfo(Long customerId, String name, int ordersCount, double totalActivity) {
        this.customerId = customerId;
        this.name = name;
        this.ordersCount = ordersCount;
        this.totalActivity = totalActivity;
    }

    // Геттеры
    public Long getCustomerId() { return customerId; }
    public String getName() { return name; }
    public int getOrdersCount() { return ordersCount; }
    public double getTotalActivity() { return totalActivity; }
}
