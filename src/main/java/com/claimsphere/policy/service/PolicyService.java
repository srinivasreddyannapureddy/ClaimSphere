package com.claimsphere.policy.service;

import com.claimsphere.policy.dto.PolicyRequestDTO;
import com.claimsphere.policy.dto.PolicyResponseDTO;

import java.util.List;

public interface PolicyService {

    PolicyResponseDTO create(PolicyRequestDTO dto);

    List<PolicyResponseDTO> getAll();

    PolicyResponseDTO getById(Long id);

}