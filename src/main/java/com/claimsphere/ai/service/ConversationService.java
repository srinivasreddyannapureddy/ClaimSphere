package com.claimsphere.ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;

@Service
public class ConversationService {

    private final ChatClient chatClient;

    public ConversationService(ChatClient.Builder builder,
                               ChatMemory chatMemory) {

        this.chatClient = builder
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build()
                )
                .build();
    }

    public String chat(String conversationId, String message) {

        return chatClient
                .prompt()
                .user(message)
                .advisors(advisor -> advisor
                        .param(
                                ChatMemory.CONVERSATION_ID,
                                conversationId
                        ))
                .call()
                .content();
    }
}
