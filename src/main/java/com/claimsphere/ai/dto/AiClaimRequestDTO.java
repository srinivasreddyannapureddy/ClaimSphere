package com.claimsphere.ai.dto;

import lombok.Data;

@Data
public class AiClaimRequestDTO {
    private String policy;
    private Double claimAmount;
    private String hospital;
    private String disease;
}
