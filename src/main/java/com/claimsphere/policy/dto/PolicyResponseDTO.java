package com.claimsphere.policy.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class PolicyResponseDTO {

    private Long id;
    private String policyNumber;
    private String policyName;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal coverageAmount;

}

