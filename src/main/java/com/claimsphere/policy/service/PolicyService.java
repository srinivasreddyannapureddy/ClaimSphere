package com.claimsphere.policy.service;

import com.claimsphere.policy.dto.PolicyRequestDTO;
import com.claimsphere.policy.dto.PolicyResponseDTO;

import java.util.List;

public interface PolicyService {

    List<PolicyResponseDTO> createPolicies(Long customerId, List<PolicyRequestDTO> requestDTOs);

    PolicyResponseDTO updatePolicy(Long id, PolicyRequestDTO requestDTO);

    PolicyResponseDTO getPolicyById(Long id);

    List<PolicyResponseDTO> getAllPolicies();

    void deletePolicy(Long id);
}