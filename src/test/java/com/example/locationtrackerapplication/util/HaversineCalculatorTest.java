package com.example.locationtrackerapplication.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HaversineCalculatorTest {

    @Test
    void testCalculateDistance() {
        // Starting point: Paris (48.8566, 2.3522)
        double startLatitude = 48.8566;
        double startLongitude = 2.3522;

        // Ending point: London (51.5074, -0.1278)
        double endLatitude = 51.5074;
        double endLongitude = -0.1278;

        // Expected distance (approx): ~343 km
        double distance = HaversineCalculator.calculateDistance(endLatitude, endLongitude);

        assertTrue(distance > 340 && distance < 350, "Distance should be approximately 343 km");
    }
}
