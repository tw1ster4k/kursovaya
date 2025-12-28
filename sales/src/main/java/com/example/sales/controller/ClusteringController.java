package com.example.sales.controller;

import com.example.sales.dto.CustomerClusterInfo;
import com.example.sales.service.analytics.ClusteringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/analytics/clustering")
public class ClusteringController {

    @Autowired
    private ClusteringService clusteringService;

    /**
     * Разбиваем клиентов на кластеры по активности и отображаем на странице
     */
    @GetMapping("/customers")
public String customerClustersPage(Model model) {
    Map<Integer, List<CustomerClusterInfo>> clusters = clusteringService.clusterCustomers();
    model.addAttribute("customerClusters", clusters);
    return "analytics/clustering";
}
}

