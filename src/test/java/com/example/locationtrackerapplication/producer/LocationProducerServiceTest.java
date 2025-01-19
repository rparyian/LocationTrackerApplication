package com.example.locationtrackerapplication.producer;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.*;

class LocationProducerServiceTest {

    @Test
    void testSendLocationUpdates() {
        KafkaTemplate<String, String> kafkaTemplate = Mockito.mock(KafkaTemplate.class);
        LocationProducerService producerService = new LocationProducerService(kafkaTemplate);

        producerService.sendLocationUpdates();

        verify(kafkaTemplate, atLeastOnce()).send(eq("location_updates"), anyString());
    }
}
