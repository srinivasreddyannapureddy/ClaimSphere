package com.claimsphere.ai.dto;

import lombok.Data;

@Data
public class ConversationRequest {

    private String conversationId;
    private String message;
}