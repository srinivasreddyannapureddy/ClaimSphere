package com.claimsphere.kafka.event;

public record ClaimCreatedEvent(
        Long claimId,
        String patientName,
        Double amount
) {
}