package com.claimsphere.policy.service;

import com.claimsphere.policy.dto.PolicyRequestDTO;
import com.claimsphere.policy.dto.PolicyResponseDTO;
import com.claimsphere.customer.entity.Customer;
import com.claimsphere.policy.entity.Policy;
import com.claimsphere.policy.enums.PolicyStatus;
import com.claimsphere.common.mapper.PolicyMapper;
import com.claimsphere.customer.repository.CustomerRepository;
import com.claimsphere.policy.repository.PolicyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PolicyServiceImpl implements PolicyService {

    private final PolicyRepository policyRepository;
    private final CustomerRepository customerRepository;
    private final PolicyMapper policyMapper;


    @Transactional
    @Override
    public List<PolicyResponseDTO> createPolicies(Long customerId, List<PolicyRequestDTO> requestDTOs) {

        if (requestDTOs == null || requestDTOs.isEmpty()) {
            throw new IllegalArgumentException("At least one policy must be provided.");
        }

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        List<Policy> policies = requestDTOs.stream()
                .map(requestDTO -> {
                    Policy policy = policyMapper.toEntity(requestDTO);

                    customer.addPolicy(policy);

                    if (policy.getEndDate().isBefore(policy.getStartDate())) {
                        throw new IllegalArgumentException("End date cannot be before start date");
                    }

                    policy.setStatus(calculateStatus(policy.getEndDate()));

                    return policy;
                })
                .toList();
        List<Policy> savedPolicies = policyRepository.saveAll(policies);

        return savedPolicies.stream()
                .map(policyMapper::toResponseDTO)
                .toList();

    }

    private PolicyStatus calculateStatus(LocalDate endDate) {
        return endDate.isBefore(LocalDate.now())
                ? PolicyStatus.EXPIRED
                : PolicyStatus.ACTIVE;
    }

    @Override
    public PolicyResponseDTO updatePolicy(Long id, PolicyRequestDTO requestDTO) {

        Policy existingPolicy = policyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Policy not found"));


        existingPolicy.setPolicyNumber(requestDTO.getPolicyNumber());
        existingPolicy.setPolicyName(requestDTO.getPolicyName());
        existingPolicy.setPolicyType(requestDTO.getPolicyType());
        existingPolicy.setCoverageAmount(requestDTO.getCoverageAmount());
        existingPolicy.setPremium(requestDTO.getPremium());
        existingPolicy.setStartDate(requestDTO.getStartDate());
        existingPolicy.setEndDate(requestDTO.getEndDate());
        existingPolicy.setStatus(calculateStatus(existingPolicy.getEndDate()));

        Policy updatedPolicy = policyRepository.save(existingPolicy);

        return policyMapper.toResponseDTO(updatedPolicy);
    }

    @Override
    public PolicyResponseDTO getPolicyById(Long id) {

        Policy policy = policyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Policy not found"));

        return policyMapper.toResponseDTO(policy);
    }

    @Override
    public List<PolicyResponseDTO> getAllPolicies() {

        return policyRepository.findAll()
                .stream()
                .map(policyMapper::toResponseDTO)
                .toList();
    }

    @Override
    public void deletePolicy(Long id) {

        Policy policy = policyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Policy not found"));

        policyRepository.delete(policy);
    }
}