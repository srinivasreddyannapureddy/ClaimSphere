package com.claimsphere.claim.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static java.lang.Thread.sleep;

@Service
@Slf4j
public class AsyncNotificationService {
    @Async("claimExecutor")
    public void sendEmail() {
        try {
            sleep(5000); // Simulate delay
            log.info(Thread.currentThread().getName());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        log.info("Email sent successfully");
    }

    @Async("claimExecutor")
    public void sendSMS() {
        try {
            sleep(1000); // Simulate delay
            log.info(Thread.currentThread().getName());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        log.info("SMS sent successfully");
    }

    @Async("claimExecutor")
    public void generateClaimPDF() {
        try {
            sleep(3000); // Simulate delay
            log.info(Thread.currentThread().getName());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        log.info("Claim PDF generated successfully");
    }


}
