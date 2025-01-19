package com.example.locationtrackerapplication.util;

public class HaversineCalculator {
    private static final int EARTH_RADIUS = 6371; // in kilometers
    private static double previousLatitude = 0;
    private static double previousLongitude = 0;

    public static double calculateDistance(double latitude, double longitude) {
        if (previousLatitude == 0 && previousLongitude == 0) {
            previousLatitude = latitude;
            previousLongitude = longitude;
            return 0;
        }

        double latDistance = Math.toRadians(latitude - previousLatitude);
        double lonDistance = Math.toRadians(longitude - previousLongitude);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(previousLatitude)) * Math.cos(Math.toRadians(latitude)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS * c;

        previousLatitude = latitude;
        previousLongitude = longitude;
        return distance;
    }
}
