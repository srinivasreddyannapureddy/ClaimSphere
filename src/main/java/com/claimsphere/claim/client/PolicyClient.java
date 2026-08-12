package com.claimsphere.claim.client;

import com.claimsphere.common.exception.PolicyNotFoundException;
import com.claimsphere.common.exception.PolicyServiceUnavailableException;
import com.claimsphere.policy.dto.PolicyResponseDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

@Component
public class PolicyClient {

    private final RestClient policyRestClient;

    public PolicyClient(RestClient policyRestClient) {
        this.policyRestClient = policyRestClient;
    }

    @Retry(name = "policyService")
    @CircuitBreaker(
            name = "policyService",
            fallbackMethod = "policyServiceFallback"
    )
    public PolicyResponseDTO getPolicy(String policyNumber) {

        try {

            return policyRestClient
                    .get()
                    .uri("/api/policies/{policyNumber}", policyNumber)
                    .retrieve()
                    .body(PolicyResponseDTO.class);

        } catch (HttpClientErrorException.NotFound ex) {

            throw new PolicyNotFoundException(
                    "Policy not found: " + policyNumber
            );

        } catch (ResourceAccessException ex) {

            throw new PolicyServiceUnavailableException(
                    "Policy Service is currently unavailable",
                    ex
            );
        }
    }

    private PolicyResponseDTO policyServiceFallback(
            String policyNumber,
            Throwable throwable) {

        throw new PolicyServiceUnavailableException(
                "Policy Service is unavailable. Circuit breaker is active.",
                throwable
        );
    }
}