package com.technical.assessment.config;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    @KafkaListener(topics = "technical.assessment",
    groupId = "my-group")
    void listener(String data) {
        System.out.println(("Listener received: " + data + " tadaaaa"));
    }
}
