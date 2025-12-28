package com.example.sales.service;

import com.example.sales.entity.Sale;
import java.util.List;

public interface SaleService {

    Sale createSale(Sale sale); // Создать продажу

    List<Sale> getAllSales(); // Получить все продажи

    Sale getSaleById(Long saleId); // Получить продажу по ID
    
    Sale updateSale(Sale sale);

    void saveSale(Sale sale);

    void deleteSale(Long saleId); // Удалить продажу

}
