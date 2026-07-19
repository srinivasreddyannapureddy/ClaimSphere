package com.claimsphere.kafka.consumer;

import com.claimsphere.kafka.event.ClaimCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ClaimConsumer {

    @KafkaListener(
            topics = "claim-created",
            groupId = "claim-group"
    )
    public void consume(ClaimCreatedEvent event) {

        log.info("Received : {}", event);
    }
}