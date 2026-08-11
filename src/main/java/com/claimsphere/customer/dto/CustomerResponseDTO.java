package com.claimsphere.customer.dto;

import com.claimsphere.policy.dto.PolicyResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDTO {

    private Long id;

    private String fullName;

    private String email;

    private String phone;

    private String address;

    private List<PolicyResponseDTO> policies;
}