package com.claimsphere.ai.dto;

import com.claimsphere.ai.model.Decision;
import com.claimsphere.ai.model.RiskLevel;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ClaimReviewResponse {

    @NotNull
    private Decision decision;

    @NotNull
    private String reason;

    @NotNull
    private RiskLevel risk;
}