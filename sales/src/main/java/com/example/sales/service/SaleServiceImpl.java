package com.example.sales.service;

import com.example.sales.entity.Sale;
import com.example.sales.repository.SaleRepository;
import com.example.sales.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Override
    public Sale createSale(Sale sale) {
        return saleRepository.save(sale);
    }

    @Override
    public Sale getSaleById(Long id) {
        return saleRepository.findById(id).orElse(null);
    }

    @Override
    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    @Override
    public void deleteSale(Long id) {
        saleRepository.deleteById(id);
    }

    @Override
    public Sale updateSale(Sale sale) {
        // Проверяем, существует ли запись
        if (sale.getId() != null && saleRepository.existsById(sale.getId())) {
            return saleRepository.save(sale);
        } else {
            // Можно выбросить исключение или просто создать новую запись
            return null;
        }
    }

    @Override
    public void saveSale(Sale sale){
        saleRepository.save(sale);
    }
}