package com.claimsphere.claim.dto;

import com.claimsphere.claim.enums.ClaimStatus;
import com.claimsphere.claim.enums.ClaimType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ClaimResponseDTO {

    private Long id;

    private String claimNumber;

    private ClaimType claimType;

    private BigDecimal claimAmount;

    private String description;

    private ClaimStatus status;

    private Long policyId;

    private String policyNumber;
}
