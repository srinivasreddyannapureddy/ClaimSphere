package com.claimsphere.policy.dto;

import com.claimsphere.policy.enums.PolicyType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PolicyRequestDTO {

    @NotBlank(message = "Policy number is required")
    private String policyNumber;

    @NotBlank(message = "Policy name is required")
    private String policyName;

    @NotNull(message = "Policy type is required")
    private PolicyType policyType;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @Future(message = "End date must be in the future")
    private LocalDate endDate;

    @NotNull(message = "Coverage amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Coverage amount must be greater than zero")
    private BigDecimal coverageAmount;

    @NotNull(message = "Premium is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Premium must be greater than zero")
    private BigDecimal premium;

    @NotNull(message = "Customer Id is required")
    private Long customerId;
}