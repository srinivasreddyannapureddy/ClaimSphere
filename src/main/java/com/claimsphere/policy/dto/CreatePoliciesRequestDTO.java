package com.claimsphere.policy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePoliciesRequestDTO {

    private List<PolicyRequestDTO> policies;

}
