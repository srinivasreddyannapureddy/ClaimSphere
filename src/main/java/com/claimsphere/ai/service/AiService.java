package com.claimsphere.ai.service;

import com.claimsphere.ai.dto.AiClaimRequestDTO;
import com.claimsphere.ai.dto.ClaimReviewResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;


import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
@Slf4j
public class AiService {
    @Value("classpath:prompts/claim-review-system.st")
    private Resource systemPromptResource;

    @Value("classpath:prompts/claim-review-user.st")
    private Resource userPromptResource;

    private final ChatClient chatClient;

    public AiService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public String ask(String question) {

        return chatClient
                .prompt()
                .user(question)
                .call()
                .content();
    }

    public ClaimReviewResponse review(AiClaimRequestDTO request) {

        BeanOutputConverter<ClaimReviewResponse> converter =
                new BeanOutputConverter<>(ClaimReviewResponse.class);

        String format = converter.getFormat();

        log.info("AI output format: {}", format);

        String systemPrompt;
        String userPromptTemplate;

        try {
            systemPrompt =
                    systemPromptResource.getContentAsString(StandardCharsets.UTF_8);

            userPromptTemplate =
                    userPromptResource.getContentAsString(StandardCharsets.UTF_8);

        } catch (IOException e) {
            log.error("Failed to load AI prompt templates", e);

            throw new IllegalStateException(
                    "Failed to load AI prompt templates",
                    e
            );
        }

        PromptTemplate promptTemplate =
                new PromptTemplate(userPromptTemplate);

        String userPrompt = promptTemplate.render(Map.of(
                "policyNumber", String.valueOf(request.getPolicy()),
                "claimAmount", String.valueOf(request.getClaimAmount()),
                "hospital", String.valueOf(request.getHospital()),
                "disease", String.valueOf(request.getDisease()),
                "format", format
        ));

        ClaimReviewResponse result = chatClient
                .prompt()
                .system(systemPrompt)
                .user(userPrompt)
                .call()
                .entity(ClaimReviewResponse.class);

        log.info("AI Review Response: {}", result);





        if (result  == null ||
                result.getDecision() == null ||
                result.getReason() == null ||
                result.getReason().isBlank() ||
                result.getRisk() == null) {

            log.error("Invalid AI response: {}", result);

            throw new IllegalStateException(
                    "AI returned incomplete claim review response"
            );
        }
        return result;
    }
}