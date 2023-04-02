package com.robo.harvexsolo.configuration.kafka;

import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.Map;

public interface KafkaConfigurationConsumerInterface {
    Map<String, Object> consumerConfig();
    ConsumerFactory<String, String> consumerFactory();
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> factory(
            ConsumerFactory<String, String> consumerFactory
    );
}
