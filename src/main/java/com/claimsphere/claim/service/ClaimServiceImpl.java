package com.claimsphere.claim.service;

import com.claimsphere.claim.dto.ClaimRequestDTO;
import com.claimsphere.claim.dto.ClaimResponseDTO;
import com.claimsphere.claim.entity.Claim;
import com.claimsphere.claim.enums.ClaimStatus;
import com.claimsphere.claim.repository.ClaimRepository;
import com.claimsphere.claim.service.ClaimService;
import com.claimsphere.common.mapper.ClaimMapper;
import com.claimsphere.policy.entity.Policy;
import com.claimsphere.policy.repository.PolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepository claimRepository;
    private final PolicyRepository policyRepository;
    private final ClaimMapper claimMapper;
    private final ClaimNumberService claimNumberService;

    @Override
    public ClaimResponseDTO createClaim(ClaimRequestDTO requestDTO) {

        Policy policy = policyRepository.findById(requestDTO.getPolicyId())
                .orElseThrow(() -> new RuntimeException("Policy not found"));

        Claim claim = claimMapper.toEntity(requestDTO);

        claim.setPolicy(policy);
        claim.setStatus(ClaimStatus.SUBMITTED);
        claim.setClaimNumber(claimNumberService.generateClaimNumber());

        Claim savedClaim = claimRepository.save(claim);

        return claimMapper.toResponseDTO(savedClaim);
    }

    @Override
    public ClaimResponseDTO updateClaim(Long id, ClaimRequestDTO requestDTO) {

        Claim existingClaim = claimRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Claim not found"));

        Policy policy = policyRepository.findById(requestDTO.getPolicyId())
                .orElseThrow(() -> new RuntimeException("Policy not found"));

        existingClaim.setClaimType(requestDTO.getClaimType());
        existingClaim.setClaimAmount(requestDTO.getClaimAmount());
        existingClaim.setDescription(requestDTO.getDescription());
        existingClaim.setPolicy(policy);

        Claim updatedClaim = claimRepository.save(existingClaim);

        return claimMapper.toResponseDTO(updatedClaim);
    }

    @Override
    public ClaimResponseDTO getClaimById(Long id) {

        Claim claim = claimRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Claim not found"));

        return claimMapper.toResponseDTO(claim);
    }

    @Override
    public List<ClaimResponseDTO> getAllClaims() {

        return claimRepository.findAll()
                .stream()
                .map(claimMapper::toResponseDTO)
                .toList();
    }

    @Override
    public void deleteClaim(Long id) {

        Claim claim = claimRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Claim not found"));

        claimRepository.delete(claim);
    }

    private String generateClaimNumber() {
        return "CLM-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
