package com.example.tawasalnaoperations.services;

import com.example.tawasalnaoperations.entities.RouteOptimizationResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RouteOptimizationService {

    private static final double AVERAGE_SPEED_KMH = 60.0; // Average speed in km/h
    private static final double FUEL_CONSUMPTION_RATE = 0.12; // Fuel consumption per km (liters/km)

    public RouteOptimizationResponse optimizeRoutes(String startLocation, String endLocation) {
        // Create a mock response for now
        RouteOptimizationResponse response = new RouteOptimizationResponse();

        // Assuming startLocation and endLocation are coordinates in the format "latitude,longitude"
        String[] startCoords = startLocation.split(",");
        String[] endCoords = endLocation.split(",");

        double startLat = Double.parseDouble(startCoords[0]);
        double startLon = Double.parseDouble(startCoords[1]);
        double endLat = Double.parseDouble(endCoords[0]);
        double endLon = Double.parseDouble(endCoords[1]);

        RouteOptimizationResponse.Route route = new RouteOptimizationResponse.Route();
        route.setRouteId("R001");

        // Calculate distance using the Haversine formula
        double distance = calculateDistance(startLat, startLon, endLat, endLon);

        // Calculate time based on distance and average speed
        double time = calculateTime(distance);

        // Calculate fuel consumption based on distance
        double fuelConsumption = calculateFuelConsumption(distance);

        route.setTotalDistance(distance);
        route.setEstimatedTime(time);
        route.setFuelConsumption(fuelConsumption);

        List<RouteOptimizationResponse.Route.Point> points = new ArrayList<>();
        points.add(new RouteOptimizationResponse.Route.Point(startLat, startLon));
        points.add(new RouteOptimizationResponse.Route.Point(endLat, endLon));

        route.setPoints(points);

        List<RouteOptimizationResponse.Route> routes = new ArrayList<>();
        routes.add(route);

        response.setRoutes(routes);
        response.setTotalDistance(distance);
        response.setTotalTime(time);
        response.setTotalFuel(fuelConsumption);

        return response;
    }

    // Implement Haversine formula to calculate distance between two points
    private double calculateDistance(double startLat, double startLon, double endLat, double endLon) {
        final int EARTH_RADIUS_KM = 6371; // Radius of the earth in kilometers

        double latDistance = Math.toRadians(endLat - startLat);
        double lonDistance = Math.toRadians(endLon - startLon);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(startLat)) * Math.cos(Math.toRadians(endLat))
                + Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c; // Distance in kilometers
    }

    // Calculate time based on distance and an average speed
    private double calculateTime(double distance) {
        return distance / AVERAGE_SPEED_KMH; // Time in hours
    }

    // Calculate fuel consumption based on distance
    private double calculateFuelConsumption(double distance) {
        return distance * FUEL_CONSUMPTION_RATE; // Fuel consumption in liters
    }
}

