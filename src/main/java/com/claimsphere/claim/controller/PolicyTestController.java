package com.claimsphere.claim.controller;

import com.claimsphere.claim.client.PolicyClient;
import com.claimsphere.policy.dto.PolicyResponseDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class PolicyTestController {

    private final PolicyClient policyClient;

    public PolicyTestController(PolicyClient policyClient) {
        this.policyClient = policyClient;
    }

    @GetMapping("/policies/{policyNumber}")
    public PolicyResponseDTO getPolicy(
            @PathVariable String policyNumber) {

        return policyClient.getPolicy(policyNumber);
    }
}