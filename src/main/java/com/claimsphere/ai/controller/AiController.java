package com.claimsphere.ai.controller;

import com.claimsphere.ai.dto.AiClaimRequestDTO;
import com.claimsphere.ai.dto.ClaimReviewResponse;
import com.claimsphere.ai.service.AiService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }


    @GetMapping("/chat")
    public String chat(
            @RequestParam String message) {

        return aiService.ask(message);
    }

    @PostMapping("/review")
    public ClaimReviewResponse review(
            @RequestBody AiClaimRequestDTO message) {

        return aiService.review(message);
    }
}