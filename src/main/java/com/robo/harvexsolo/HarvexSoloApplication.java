package com.robo.harvexsolo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class HarvexSoloApplication {

    public static void main(String[] args) {
        SpringApplication.run(HarvexSoloApplication.class, args);
    }

    CommandLineRunner commandLineRunner(KafkaTemplate<String, String> kafkaTemplate) {
        return args -> {
          kafkaTemplate.send("kafkaTestTopic", "Hello world!");
        };
    }

}
