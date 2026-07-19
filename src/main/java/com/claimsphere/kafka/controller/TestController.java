package com.claimsphere.kafka.controller;

import com.claimsphere.kafka.event.ClaimCreatedEvent;
import com.claimsphere.kafka.producer.ClaimProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/kafka")
public class TestController {

    private final ClaimProducer producer;

    @PostMapping("/publish")
    public String publish(@RequestBody ClaimCreatedEvent eventRequest) {


        producer.publish(eventRequest);

        return "Published";
    }
}