package com.example.sales.service.analytics;

import com.example.sales.entity.Sale;
import com.example.sales.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Линейная регрессия для прогнозирования продаж
 */
@Service
public class RegressionService {

    @Autowired
    private SaleRepository saleRepository;

    /**
     * Прогнозируем продажи на следующую дату методом простой линейной регрессии
     */
    public BigDecimal predictNextSale() {
        List<Sale> sales = saleRepository.findAll();

        // Группируем продажи по дате
        Map<LocalDate, BigDecimal> dailySales = sales.stream()
                .collect(Collectors.groupingBy(
                        s -> s.getSaleDate().toLocalDate(),
                        Collectors.mapping(Sale::getTotalAmount,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
                ));

        int n = dailySales.size();
        if (n < 2) return BigDecimal.ZERO;

        // Преобразуем в массивы для расчета линейной регрессии y = a + bx
        double[] x = new double[n];
        double[] y = new double[n];
        int i = 0;
        for (Map.Entry<LocalDate, BigDecimal> entry : dailySales.entrySet()) {
            x[i] = i + 1; // день как индекс
            y[i] = entry.getValue().doubleValue();
            i++;
        }

        double xMean = 0, yMean = 0;
        for (int j = 0; j < n; j++) {
            xMean += x[j];
            yMean += y[j];
        }
        xMean /= n;
        yMean /= n;

        double numerator = 0;
        double denominator = 0;
        for (int j = 0; j < n; j++) {
            numerator += (x[j] - xMean) * (y[j] - yMean);
            denominator += (x[j] - xMean) * (x[j] - xMean);
        }
        double b = numerator / denominator;
        double a = yMean - b * xMean;

        double nextX = n + 1;
        double prediction = a + b * nextX;
        return BigDecimal.valueOf(prediction);
    }
}
