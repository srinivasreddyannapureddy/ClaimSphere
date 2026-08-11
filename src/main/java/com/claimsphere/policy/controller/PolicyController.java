package com.claimsphere.policy.controller;

import com.claimsphere.policy.dto.CreatePoliciesRequestDTO;
import com.claimsphere.policy.dto.PolicyRequestDTO;
import com.claimsphere.policy.dto.PolicyResponseDTO;
import com.claimsphere.policy.service.PolicyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/policies")
@RequiredArgsConstructor
public class PolicyController {

    private final PolicyService policyService;

    @PostMapping("/customers/{customerId}")
    public ResponseEntity<List<PolicyResponseDTO>> createPolicies(
            @PathVariable Long customerId,
            @RequestBody CreatePoliciesRequestDTO request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(policyService.createPolicies(customerId, request.getPolicies()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PolicyResponseDTO> getPolicyById(@PathVariable Long id) {
        return ResponseEntity.ok(policyService.getPolicyById(id));
    }

    @GetMapping
    public ResponseEntity<List<PolicyResponseDTO>> getAllPolicies() {
        return ResponseEntity.ok(policyService.getAllPolicies());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PolicyResponseDTO> updatePolicy(
            @PathVariable Long id,
            @Valid @RequestBody PolicyRequestDTO requestDTO) {

        return ResponseEntity.ok(policyService.updatePolicy(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePolicy(@PathVariable Long id) {

        policyService.deletePolicy(id);
        return ResponseEntity.noContent().build();
    }
}