package com.example.sales.service;

import com.example.sales.entity.Customer;
import com.example.sales.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        Customer existing = customerRepository.findById(id).orElse(null);
        if (existing != null) {
            if (Objects.nonNull(customer.getName()) && !customer.getName().isEmpty()) {
                existing.setName(customer.getName());
            }
            if (Objects.nonNull(customer.getCategory()) && !customer.getCategory().isEmpty()) {
                existing.setCategory(customer.getCategory());
            }
            if (Objects.nonNull(customer.getRegion()) && !customer.getRegion().isEmpty()) {
                existing.setRegion(customer.getRegion());
            }
            return customerRepository.save(existing);
        }
        return null;
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
