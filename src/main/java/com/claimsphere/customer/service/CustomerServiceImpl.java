package com.claimsphere.customer.service;

import com.claimsphere.customer.dto.CustomerRequestDTO;
import com.claimsphere.customer.dto.CustomerResponseDTO;
import com.claimsphere.customer.entity.Customer;
import com.claimsphere.customer.mapper.CustomerMapper;
import com.claimsphere.customer.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    @Override
    public CustomerResponseDTO createCustomer(CustomerRequestDTO dto) {

        Customer customer = mapper.toEntity(dto);

        repository.save(customer);

        return mapper.toResponse(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponseDTO getCustomer(Long id) {

        Customer customer = repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Customer not found with id : " + id));

        return mapper.toResponse(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponseDTO> getAllCustomers() {

        return mapper.toResponseList(repository.findAll());
    }

    @Override
    public CustomerResponseDTO updateCustomer(Long id,
                                              CustomerRequestDTO dto) {

        Customer customer = repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Customer not found with id : " + id));

        mapper.updateCustomer(dto, customer);

        repository.save(customer);

        return mapper.toResponse(customer);
    }

    @Override
    public void deleteCustomer(Long id) {

        Customer customer = repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Customer not found with id : " + id));

        repository.delete(customer);
    }
}