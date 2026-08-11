package com.claimsphere.ai.service;


import com.claimsphere.ai.dto.ClaimReviewResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PolicyRagService {

    private final VectorStore vectorStore;
    private final ChatClient chatClient;

    public PolicyRagService(
            VectorStore vectorStore,
            ChatClient.Builder chatClientBuilder) {

        this.vectorStore = vectorStore;
        this.chatClient = chatClientBuilder.build();
    }

    public ClaimReviewResponse reviewClaim(String claimText) {

        // 1. Retrieve relevant policy documents
        List<Document> documents = retrievePolicies(claimText);

        // 2. Build context from retrieved documents
        String policyContext = buildContext(documents);

        // 3. Build RAG prompt
        String prompt = buildPrompt(claimText, policyContext);

        // 4. Send context + claim to LLM
        return chatClient.prompt()
                .user(prompt)
                .call()
                .entity(ClaimReviewResponse.class);
    }

    private List<Document> retrievePolicies(String claimText) {

        SearchRequest searchRequest = SearchRequest.builder()
                .query(claimText)
                .topK(3)
                .similarityThreshold(0.60)
                .build();

        List<Document> documents =
                vectorStore.similaritySearch(searchRequest);

        log.info("\n========== RAG RETRIEVAL ==========");
        log.info("CLAIM: " + claimText);

        if (documents == null || documents.isEmpty()) {
            log.info("No relevant documents found.");
        } else {
            for (Document document : documents) {

                log.info("-------------------------------");
                log.info(
                        "Policy: " +
                                document.getMetadata().get("policyNumber")
                );

                log.info(
                        "Type: " +
                                document.getMetadata().get("documentType")
                );

                log.info(
                        "Text: " +
                                document.getText()
                );

                log.info(
                        "Score: " +
                                document.getScore()
                );
            }
        }

        log.info("====================================\n");

        return documents;
    }

    private String buildContext(List<Document> documents) {

        if (documents == null || documents.isEmpty()) {
            return "NO RELEVANT POLICY INFORMATION FOUND.";
        }

        return documents.stream()
                .map(document -> {

                    String policyNumber =
                            String.valueOf(
                                    document.getMetadata()
                                            .getOrDefault("policyNumber", "UNKNOWN")
                            );

                    String documentType =
                            String.valueOf(
                                    document.getMetadata()
                                            .getOrDefault("documentType", "UNKNOWN")
                            );

                    return """
                            Policy Number: %s
                            Document Type: %s
                            Policy Text:
                            %s
                            """.formatted(
                            policyNumber,
                            documentType,
                            document.getText()
                    );
                })
                .collect(Collectors.joining("\n--------------------\n"));
    }

    private String buildPrompt(
            String claimText,
            String policyContext) {

        return """
                You are an insurance claim review assistant.

                Your task is to review the claim using ONLY the
                policy information provided in the context.

                POLICY CONTEXT:
                ====================
                %s
                ====================

                CLAIM:
                %s

                RULES:

                1. Use only the policy context provided above.
                2. Do not invent or assume policy coverage.
                3. If the policy context does not contain enough
                   information to determine coverage, return PEND.
                4. If the claim is clearly covered, return APPROVED.
                5. If the claim is clearly excluded, return DENIED.
                6. Explain the decision using the retrieved policy.
                7. Risk must be LOW, MEDIUM, HIGH, or UNKNOWN.
                8. Return ONLY the required JSON structure.

                Required JSON structure:

                {
                  "decision": "APPROVED | DENIED | PEND",
                  "reason": "reason for the decision",
                  "risk": "LOW | MEDIUM | HIGH | UNKNOWN"
                }
                """.formatted(policyContext, claimText);
    }
}
