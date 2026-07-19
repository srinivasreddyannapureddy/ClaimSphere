package com.claimsphere.kafka.producer;

import com.claimsphere.kafka.event.ClaimCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClaimProducer {

    private final KafkaTemplate<String, ClaimCreatedEvent> kafkaTemplate;

    public void publish(ClaimCreatedEvent event) {

        kafkaTemplate.send("claim-created-v2", event.claimId().toString(), event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Published successfully. Offset={}",
                                result.getRecordMetadata().offset());

                        var metadata = result.getRecordMetadata();

                        log.info("""
                    Published Successfully
                    Topic     : {}
                    Partition : {}
                    Offset    : {}
                    Key       : {}
                    """,
                                metadata.topic(),
                                metadata.partition(),
                                metadata.offset(),
                                event.claimId());
                    } else {
                        log.error("Failed to publish", ex);
                    }
                });
    }
}