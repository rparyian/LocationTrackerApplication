package com.example.locationtrackerapplication.producer;

import com.example.locationtrackerapplication.model.LocationUpdater;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class LocationProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Random random = new Random();

    public LocationProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendLocationUpdates() {
        new Thread(() -> {
            while (true) {
                double latitude = random.nextDouble() * 180 - 90; // Random latitude
                double longitude = random.nextDouble() * 360 - 180; // Random longitude

                LocationUpdater locationUpdate = new LocationUpdater(latitude, longitude);
                kafkaTemplate.send("location_updates", locationUpdate.toString());

                System.out.println("Sent: " + locationUpdate);
                try {
                    Thread.sleep(2000); // Send every 2 seconds
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }).start();
    }
}