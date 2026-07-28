package com.claimsphere.claim.dto;

import com.claimsphere.claim.enums.ClaimType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ClaimRequestDTO {

    @NotNull
    private ClaimType claimType;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal claimAmount;

    @NotBlank
    private String description;

    @NotNull
    private Long policyId;
}