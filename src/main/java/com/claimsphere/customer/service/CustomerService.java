package com.claimsphere.customer.service;

import com.claimsphere.customer.dto.CustomerRequestDTO;
import com.claimsphere.customer.dto.CustomerResponseDTO;

import java.util.List;

public interface CustomerService {

    CustomerResponseDTO createCustomer(CustomerRequestDTO dto);

    CustomerResponseDTO getCustomer(Long id);

    List<CustomerResponseDTO> getAllCustomers();

    CustomerResponseDTO updateCustomer(Long id,
                                       CustomerRequestDTO dto);

    void deleteCustomer(Long id);
}