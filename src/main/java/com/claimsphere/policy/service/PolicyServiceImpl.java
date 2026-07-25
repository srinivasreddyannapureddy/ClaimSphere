package com.claimsphere.policy.service;

import com.claimsphere.policy.dto.PolicyRequestDTO;
import com.claimsphere.policy.dto.PolicyResponseDTO;
import com.claimsphere.policy.entity.Policy;
import com.claimsphere.policy.repository.PolicyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PolicyServiceImpl
        implements PolicyService {

    private final PolicyRepository repository;

    @Override
    public PolicyResponseDTO create(PolicyRequestDTO dto) {

        Policy policy = Policy.builder()
                .policyNumber(dto.getPolicyNumber())
                .policyName(dto.getPolicyName())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .coverageAmount(dto.getCoverageAmount())
                .build();

        policy = repository.save(policy);

        return PolicyResponseDTO.builder()
                .id(policy.getId())
                .policyNumber(policy.getPolicyNumber())
                .policyName(policy.getPolicyName())
                .startDate(policy.getStartDate())
                .endDate(policy.getEndDate())
                .coverageAmount(policy.getCoverageAmount())
                .build();
    }

    @Override
    public List<PolicyResponseDTO> getAll() {

        return repository.findAll()
                .stream()
                .map(c -> PolicyResponseDTO.builder()
                        .id(c.getId())
                        .policyNumber(c.getPolicyNumber())
                        .policyName(c.getPolicyName())
                        .startDate(c.getStartDate())
                        .endDate(c.getEndDate())
                        .coverageAmount(c.getCoverageAmount())
                        .build())
                .toList();
    }

    @Override
    public PolicyResponseDTO getById(Long id) {

        Policy c = repository.findById(id)
                .orElseThrow();

        return PolicyResponseDTO.builder()
                .id(c.getId())
                .coverageAmount(c.getCoverageAmount())
                .policyName(c.getPolicyName())
                .policyNumber(c.getPolicyNumber())
                .startDate(c.getStartDate())
                .endDate(c.getEndDate())
                .build();
    }

}

