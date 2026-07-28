package com.claimsphere.claim.service;

import com.claimsphere.claim.dto.ClaimRequestDTO;
import com.claimsphere.claim.dto.ClaimResponseDTO;

import java.util.List;

public interface ClaimService {

    ClaimResponseDTO createClaim(ClaimRequestDTO requestDTO);

    ClaimResponseDTO updateClaim(Long id, ClaimRequestDTO requestDTO);

    ClaimResponseDTO getClaimById(Long id);

    List<ClaimResponseDTO> getAllClaims();

    void deleteClaim(Long id);
}
