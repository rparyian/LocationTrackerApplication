package com.example.locationtrackerapplication.streams;

import com.example.locationtrackerapplication.util.HaversineCalculator;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class StreamsService {

    private static final String INPUT_TOPIC = "location_updates";
    private static final String OUTPUT_TOPIC = "distance_reports";

    public void startStream() {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "location-tracker-app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> locationStream = builder.stream(INPUT_TOPIC);

        KTable<String, Double> distanceTable = locationStream
                .groupByKey()
                .aggregate(
                        () -> 0.0,
                        (key, value, aggregate) -> {
                            String[] coordinates = value.split(",");
                            double latitude = Double.parseDouble(coordinates[0]);
                            double longitude = Double.parseDouble(coordinates[1]);

                            double distance = HaversineCalculator.calculateDistance(latitude, longitude);
                            return aggregate + distance;
                        },
                        Materialized.<String, Double, KeyValueStore<org.apache.kafka.common.utils.Bytes, byte[]>>as("distance-store")
                                .withKeySerde(Serdes.String())
                                .withValueSerde(Serdes.Double())
                );

        distanceTable.toStream().to(OUTPUT_TOPIC);

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }

    public Topology buildTopology() {
        StreamsBuilder builder = new StreamsBuilder();

        // Source topic: location_updates
        KStream<String, String> locationUpdates = builder.stream("location_updates");

        // Process logic (example: store the most recent location for each user)
        KTable<String, String> userLocations = locationUpdates
                .groupByKey()
                .reduce((oldValue, newValue) -> newValue, Materialized.as("user-locations-store"));

        // Destination topic: distance_reports (as an example of aggregation or reporting)
        userLocations
                .toStream()
                .to("distance_reports");

        return builder.build();
    }
}