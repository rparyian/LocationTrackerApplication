package com.example.locationtrackerapplication.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic locationUpdatesTopic() {
        return new NewTopic("location_updates", 1, (short) 1);
    }

    @Bean
    public NewTopic distanceReportsTopic() {
        return new NewTopic("distance_reports", 1, (short) 1);
    }
}
