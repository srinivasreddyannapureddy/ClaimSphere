package com.claimsphere.ai.controller;

import com.claimsphere.ai.dto.ClaimReviewResponse;
import com.claimsphere.ai.service.PolicyRagService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/claims")
public class ClaimRagController {

    private final PolicyRagService policyRagService;

    public ClaimRagController(PolicyRagService policyRagService) {
        this.policyRagService = policyRagService;
    }

    @PostMapping("/review-rag")
    public ClaimReviewResponse reviewClaim(
            @RequestParam String claim) {

        return policyRagService.reviewClaim(claim);
    }
}