package com.claimsphere.claim.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient policyRestClient(
            @Value("${policy-service.url}") String policyServiceUrl) {

        return RestClient.builder()
                .baseUrl(policyServiceUrl)
                .requestFactory(new SimpleClientHttpRequestFactory() {{
                    setConnectTimeout(3000);
                    setReadTimeout(3000);
                }})
                .build();
    }
}
