package com.example.tawasalnaoperations.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RouteOptimizationResponse {
    private List<Route> routes;
    private double totalDistance;
    private double totalTime;
    private double totalFuel;

    // Inner class for Route details
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Route {
        private String routeId;
        private double totalDistance;
        private double estimatedTime;
        private double fuelConsumption;
        private List<Point> points;

        // Inner class for Point details
        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Point {
            private double lat;
            private double lng;
        }
    }
}
