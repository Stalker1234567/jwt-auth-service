package com.robo.harvexsolo.configuration.kafka;

import org.springframework.stereotype.Component;

@Component
public class KafkaListener {

    @org.springframework.kafka.annotation.KafkaListener(
            topics = "kafkaTestTopic",
            groupId = "groudId"
    )
    void listener(String data) {
        System.out.println("Test: " + data);
    }
}
