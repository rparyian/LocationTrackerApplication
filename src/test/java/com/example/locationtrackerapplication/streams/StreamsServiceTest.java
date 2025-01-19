package com.example.locationtrackerapplication.streams;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StreamsServiceTest {

    private TopologyTestDriver testDriver;
    private TestInputTopic<String, String> inputTopic;
    private TestOutputTopic<String, Double> outputTopic;

    @BeforeEach
    void setup() {
        StreamsService streamsService = new StreamsService();

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "test-app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "dummy:1234");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        Topology topology = streamsService.buildTopology();
        testDriver = new TopologyTestDriver(topology, props);

        inputTopic = testDriver.createInputTopic(
                "location_updates",
                Serdes.String().serializer(),
                Serdes.String().serializer()
        );

        outputTopic = testDriver.createOutputTopic(
                "distance_reports",
                Serdes.String().deserializer(),
                Serdes.Double().deserializer()
        );
    }

    @AfterEach
    void teardown() {
        testDriver.close();
    }

    @Test
    void testStreamProcessing() {
        // Simulate sending location updates
        inputTopic.pipeInput("key1", "48.8566,2.3522");
        inputTopic.pipeInput("key1", "51.5074,-0.1278");

        // Check aggregated output
        assertEquals(343.0, outputTopic.readValue(), 10.0); // Allow small margin of error
    }
}
