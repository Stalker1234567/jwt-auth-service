package com.robo.harvexsolo.configuration;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

public interface KafkaConfigurationProducerInterface {
    Map<String, Object> producerConfig();
    ProducerFactory<String, String> producerFactory();
    KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String, String> producerFactory);
}
