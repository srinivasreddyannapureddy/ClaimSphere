package com.claimsphere.policy.dto;


import com.claimsphere.policy.enums.PolicyStatus;
import com.claimsphere.policy.enums.PolicyType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class PolicyResponseDTO {

    private Long id;

    private String policyNumber;

    private String policyName;

    private PolicyType policyType;

    private LocalDate startDate;

    private LocalDate endDate;

    private BigDecimal coverageAmount;

    private BigDecimal premium;

    private PolicyStatus status;

    private Long customerId;

    private String customerName;

    private int totalClaims;
}

