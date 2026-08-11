package com.claimsphere.ai.service;



import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class PolicyVectorStoreTest implements CommandLineRunner {

    private final PolicyVectorStoreService policyVectorStoreService;

    public PolicyVectorStoreTest(
            PolicyVectorStoreService policyVectorStoreService) {
        this.policyVectorStoreService = policyVectorStoreService;
    }

    @Override
    public void run(String... args) {

        String policy = """
                Policy Number: POL-1001

                Coverage:
                Hospitalization expenses related to Dengue treatment are covered.

                Waiting Period:
                There is no waiting period for Dengue treatment.

                Exclusions:
                Outpatient consultation without hospitalization is not covered.

                Maximum Coverage:
                Hospitalization expenses are covered up to INR 5,00,000.
                """;

        String kidneyPolicy = """
Policy Number: POL-2001

Coverage:
Hospitalization expenses related to kidney transplant procedures are covered.

Waiting Period:
There is a waiting period of 12 months for kidney transplant treatment.

Exclusions:
Outpatient consultation without hospitalization is not covered.

Maximum Coverage:
Kidney transplant hospitalization expenses are covered up to INR 10,00,000.
""";

        policyVectorStoreService.addPolicy(
                "POL-2001",
                kidneyPolicy
        );
        policyVectorStoreService.addPolicy(
                "POL-1001",
                policy
        );

        var results = policyVectorStoreService.searchPolicy(
                "Is hospitalization for kidney transplant covered?", "POL-2001", 3
        );

        results.forEach(document -> {
            log.info("----- MATCH -----");
            log.info(document.getText());
            log.info("Metadata: " + document.getMetadata());
        });

        String answer =
                policyVectorStoreService.answerPolicyQuestion(
                        "Is chemotherapy covered under this policy?",
                        "POL-2001"
                );

        log.info("===== AI ANSWER =====");
        log.info(answer);
    }
}
