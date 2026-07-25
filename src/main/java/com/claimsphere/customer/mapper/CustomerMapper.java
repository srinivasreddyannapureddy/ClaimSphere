package com.claimsphere.customer.mapper;

import com.claimsphere.customer.dto.CustomerRequestDTO;
import com.claimsphere.customer.dto.CustomerResponseDTO;
import com.claimsphere.customer.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer toEntity(CustomerRequestDTO dto);

    CustomerResponseDTO toResponse(Customer customer);

    List<CustomerResponseDTO> toResponseList(List<Customer> customers);

    void updateCustomer(CustomerRequestDTO dto,
                        @MappingTarget Customer customer);
}