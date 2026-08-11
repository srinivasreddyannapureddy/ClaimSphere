package com.claimsphere.ai.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmbeddingService {

    private final EmbeddingModel embeddingModel;

    public EmbeddingService(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    public float[] generateEmbedding(String text) {
        testSimilarity();
        return embeddingModel.embed(text);
    }

    public double cosineSimilarity(float[] a, float[] b) {

        double dotProduct = 0.0;
        double magnitudeA = 0.0;
        double magnitudeB = 0.0;

        for (int i = 0; i < a.length; i++) {
            dotProduct += a[i] * b[i];
            magnitudeA += a[i] * a[i];
            magnitudeB += b[i] * b[i];
        }

        return dotProduct /
                (Math.sqrt(magnitudeA) * Math.sqrt(magnitudeB));
    }


    public void testSimilarity() {

        String textA =
                "Dengue hospitalization expenses are covered under the policy.";

        String textB =
                "The policy covers hospital treatment for Dengue.";

        String textC =
                "The customer wants to update their email address.";

        float[] vectorA = embeddingModel.embed(textA);
        float[] vectorB = embeddingModel.embed(textB);
        float[] vectorC = embeddingModel.embed(textC);

        log.info(
                "A vs B = " + cosineSimilarity(vectorA, vectorB)
        );

        log.info(
                "A vs C = " + cosineSimilarity(vectorA, vectorC)
        );
    }

}