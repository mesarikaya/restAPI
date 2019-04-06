package com.mes.gotogether.services.domain;

public class GeoLocationThreshold {

    private final double latMin;
    private final double latMax;
    private final double longMin;
    private final double longMax;
    private final double radius;
    private final double latitude;
    private final double longitude;

    public GeoLocationThreshold(double latitude, double longitude, double radius) {

        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;

        // Set the ranges
        // All distance is assumed to be in km
        // Earth radius is in km ~= 6371
        double earthRadius = 6371;

        this.latMin = latitude - Math.toDegrees(radius/earthRadius);
        this.latMax = latitude + Math.toDegrees(radius/earthRadius);
        this.longMin = longitude - Math.toDegrees(radius/earthRadius/Math.cos(Math.toRadians(latitude)));
        this.longMax = longitude - Math.toDegrees(radius/earthRadius/Math.cos(Math.toRadians(latitude)));
    }

    public double getLatMin() {
        return latMin;
    }

    public double getLatMax() {
        return latMax;
    }

    public double getLongMin() {
        return longMin;
    }

    public double getLongMax() {
        return longMax;
    }

    public double getRadius() {
        return radius;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
