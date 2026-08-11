package com.claimsphere.ai.service;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PolicyVectorStoreService {

    private final VectorStore vectorStore;
    private final ChatClient chatClient;

    public PolicyVectorStoreService(
            VectorStore vectorStore,
            ChatClient.Builder chatClientBuilder) {

        this.vectorStore = vectorStore;
        this.chatClient = chatClientBuilder.build();
    }



    public void addPolicy(String policyNumber, String policyText) {

        Map<String, Object> metadata = Map.of(
                "policyNumber", policyNumber,
                "documentType", "POLICY"
        );

        Document document = new Document(policyText, metadata);

        vectorStore.add(List.of(document));
    }

    public List<Document> searchPolicy(String query, String policyNumber, int topK) {

        return vectorStore.similaritySearch(SearchRequest.builder()
                .query(query)
                .topK(topK)
                .filterExpression(
                        "policyNumber == '" + policyNumber + "'"
                )
                .build());
    }

    public String answerPolicyQuestion(
            String query,
            String policyNumber) {

        List<Document> documents = searchPolicy(
                query,
                policyNumber,
                3
        );

        String context = documents.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n\n"));

        String prompt = """
                You are an insurance policy assistant.
                
                                                                 Your ONLY source of truth is the policy information provided
                                                                 below.
                
                                                                 STRICT RULES:
                                                                 1. Do not use your general medical or insurance knowledge.
                                                                 2. Do not make assumptions or inferences.
                                                                 3. Do not infer coverage from the absence of an exclusion.
                                                                 4. If the requested treatment or coverage is not explicitly
                                                                    mentioned in the policy, say:
                                                                    "INSUFFICIENT INFORMATION"
                                                                 5. Do not add information that is not explicitly present
                                                                    in the policy.
                
                                                                 Policy information:
                                                                 %s
                
                                                                 Question:
                                                                 %s
                
                                                                 Answer:
            """.formatted(context, query);

        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }
}
