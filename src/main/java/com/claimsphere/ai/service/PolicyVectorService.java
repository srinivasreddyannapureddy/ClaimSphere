package com.claimsphere.ai.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class PolicyVectorService {

    private final VectorStore vectorStore;

    public PolicyVectorService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public void addPolicy(String policyText,
                          String policyId,
                          String disease,
                          String section) {

        Map<String, Object> metadata = Map.of(
                "policyId", policyId,
                "disease", disease,
                "section", section
        );

        Document document = new Document(
                policyText,
                metadata
        );

        vectorStore.add(List.of(document));
    }

    public List<Document> searchPolicy(String query, String policyId) {

        SearchRequest request = SearchRequest.builder()
                .query(query)
                .topK(3)
                .filterExpression("policyId == '" + policyId + "'")
                .build();

        return vectorStore.similaritySearch(request);
    }
}