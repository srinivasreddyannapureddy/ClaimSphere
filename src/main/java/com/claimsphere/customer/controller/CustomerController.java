package com.claimsphere.customer.controller;

import com.claimsphere.customer.dto.CustomerRequestDTO;
import com.claimsphere.customer.dto.CustomerResponseDTO;
import com.claimsphere.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(
            @Valid @RequestBody CustomerRequestDTO dto) {

        CustomerResponseDTO response =
                service.createCustomer(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public CustomerResponseDTO getCustomer(
            @PathVariable Long id) {

        return service.getCustomer(id);
    }

    @GetMapping
    public List<CustomerResponseDTO> getAllCustomers() {

        return service.getAllCustomers();
    }

    @PutMapping("/{id}")
    public CustomerResponseDTO updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody CustomerRequestDTO dto) {

        return service.updateCustomer(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(
            @PathVariable Long id) {

        service.deleteCustomer(id);
    }
}