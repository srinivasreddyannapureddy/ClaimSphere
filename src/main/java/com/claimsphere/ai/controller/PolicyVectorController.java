package com.claimsphere.ai.controller;

import com.claimsphere.ai.service.PolicyVectorService;
import org.springframework.ai.document.Document;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ai/policy")
public class PolicyVectorController {

    private final PolicyVectorService policyVectorService;

    public PolicyVectorController(PolicyVectorService policyVectorService) {
        this.policyVectorService = policyVectorService;
    }

    @PostMapping
    public String addPolicy(
            @RequestParam String policyId,
            @RequestParam String disease,
            @RequestParam String section,
            @RequestBody String policy) {

        policyVectorService.addPolicy(
                policy,
                policyId,
                disease,
                section
        );

        return "Policy added successfully";
    }

    @GetMapping("/search")
    public List<Document> search(
            @RequestParam String query,
            @RequestParam String policyId) {

        return policyVectorService.searchPolicy(query, policyId);
    }
}
