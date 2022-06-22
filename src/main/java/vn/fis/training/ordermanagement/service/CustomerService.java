package vn.fis.training.ordermanagement.service;

import vn.fis.training.ordermanagement.domain.Customer;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(Customer customer);
    Customer updateCustomer(Customer customer);
    void deleteCustomerById(Long customerId);
    List<Customer> findAll();
    Customer findByMobileNumber(String mobileNumber);
}
