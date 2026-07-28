package com.claimsphere.policy.service;

import com.claimsphere.policy.dto.PolicyRequestDTO;
import com.claimsphere.policy.dto.PolicyResponseDTO;
import com.claimsphere.customer.entity.Customer;
import com.claimsphere.policy.entity.Policy;
import com.claimsphere.policy.enums.PolicyStatus;
import com.claimsphere.common.mapper.PolicyMapper;
import com.claimsphere.customer.repository.CustomerRepository;
import com.claimsphere.policy.repository.PolicyRepository;
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

    @Override
    public PolicyResponseDTO createPolicy(PolicyRequestDTO requestDTO) {

        Customer customer = customerRepository.findById(requestDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Policy policy = policyMapper.toEntity(requestDTO);

        policy.setCustomer(customer);

        if (policy.getEndDate().isBefore(LocalDate.now())) {
            policy.setStatus(PolicyStatus.EXPIRED);
        } else {
            policy.setStatus(PolicyStatus.ACTIVE);
        }

        Policy savedPolicy = policyRepository.save(policy);

        return policyMapper.toResponseDTO(savedPolicy);
    }

    @Override
    public PolicyResponseDTO updatePolicy(Long id, PolicyRequestDTO requestDTO) {

        Policy existingPolicy = policyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Policy not found"));

        Customer customer = customerRepository.findById(requestDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        existingPolicy.setPolicyNumber(requestDTO.getPolicyNumber());
        existingPolicy.setPolicyName(requestDTO.getPolicyName());
        existingPolicy.setPolicyType(requestDTO.getPolicyType());
        existingPolicy.setCoverageAmount(requestDTO.getCoverageAmount());
        existingPolicy.setPremium(requestDTO.getPremium());
        existingPolicy.setStartDate(requestDTO.getStartDate());
        existingPolicy.setEndDate(requestDTO.getEndDate());
        existingPolicy.setCustomer(customer);

        if (existingPolicy.getEndDate().isBefore(LocalDate.now())) {
            existingPolicy.setStatus(PolicyStatus.EXPIRED);
        } else {
            existingPolicy.setStatus(PolicyStatus.ACTIVE);
        }

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