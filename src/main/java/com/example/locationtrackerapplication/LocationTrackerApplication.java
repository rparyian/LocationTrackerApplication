package com.example.locationtrackerapplication;

import com.example.locationtrackerapplication.streams.StreamsService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class LocationTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LocationTrackerApplication.class, args);

        StreamsService streamsService = new StreamsService();
        streamsService.startStream();
    }

}
