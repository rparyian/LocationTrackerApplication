Overview
The service consumes location data (longitude and latitude) from a Kafka topic, processes it using Kafka Streams, and generates periodic reports. The reports contain the total distance traveled, calculated based on the Haversine formula between consecutive coordinates.

Features:
Simulates a person’s location updates over time.
Uses Kafka Streams to process and track coordinates.
Calculates the total distance traveled using the Haversine formula.
Generates periodic reports with the calculated total distance.
Components
Kafka Producer: Simulates and sends location updates (longitude and latitude) to a Kafka topic.
Kafka Streams: Consumes the location data from the Kafka topic, processes it, and calculates the total distance using the Haversine formula.
Haversine Formula
The Haversine formula is used to calculate the shortest distance between two points on the surface of a sphere (Earth) based on their latitude and longitude.
a = sin²(Δφ/2) + cos(φ1) ⋅ cos(φ2) ⋅ sin²(Δλ/2)
c = 2 ⋅ atan2(√a, √(1−a))
distance = R ⋅ c

Requirements
Apache Kafka
Kafka Streams library
Java 8+ (or higher)
Setup
1. Install Apache Kafka
Follow the Kafka Quickstart guide to set up a Kafka instance.

2. Clone the Repository
git clone https://github.com/yourusername/location-tracker-service.git
cd location-tracker-service

3. Build the Service
If you're using Maven:
mvn clean install
If you're using Gradle:
gradle build

4. Configure Kafka
Make sure your kafka.properties file is configured correctly for the Kafka broker, including the correct topic names.


5. Run the Application
To start the Kafka producer and simulate location updates:

mvn exec:java -Dexec.mainClass="com.example.LocationProducer"
To start the Kafka Streams application for processing the data:
mvn exec:java -Dexec.mainClass="com.example.LocationStreamProcessor"

6. Access the Generated Reports
The reports, which include the total distance traveled, are sent to the distance-report Kafka topic. You can consume these reports using a Kafka consumer:

mvn exec:java -Dexec.mainClass="com.example.LocationReportConsumer"
Usage
The Kafka producer will simulate location updates at regular intervals and send them to the location-input topic.
The Kafka Streams processor will consume the location updates, calculate the total distance traveled using the Haversine formula, and generate periodic reports.
The generated reports will be sent to the distance-report topic, where you can track the total distance.
Example Location Data
Sample location data (longitude, latitude) sent to the Kafka input topic:

{
  "latitude": 40.748817,
  "longitude": -73.985428
}
Periodic Report Output
Sample report sent to the Kafka output topic:

{
  "totalDistanceKm": 12.34
}
Contributing
Feel free to fork this repository and submit pull requests for any improvements or additional features!

License
This project is licensed under the MIT License.
