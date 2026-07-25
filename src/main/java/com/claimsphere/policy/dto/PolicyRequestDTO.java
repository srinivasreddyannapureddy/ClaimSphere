package com.claimsphere.policy.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PolicyRequestDTO {

    private String policyNumber;
    private String policyName;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal coverageAmount;

}
