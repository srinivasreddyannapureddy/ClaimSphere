package com.claimsphere.ai.controller;

import com.claimsphere.ai.dto.ConversationRequest;
import com.claimsphere.ai.service.ConversationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai/conversation")
public class ConversationController {

    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @PostMapping
    public String chat(@RequestBody ConversationRequest request) {

        return conversationService.chat(
                request.getConversationId(),
                request.getMessage()
        );
    }
}
