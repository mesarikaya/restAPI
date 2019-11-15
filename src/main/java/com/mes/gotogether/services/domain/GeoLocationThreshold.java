package com.mes.gotogether.services.domain;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class GeoLocationThreshold {

    private final double latMin;
    private final double latMax;
    private final double longMin;
    private final double longMax;
    private final double radius;
    private final double latitude;
    private final double longitude;

    GeoLocationThreshold(double latitude, double longitude, double radius) {

        // All distance is assumed to be in km
        // Earth radius is in km ~= 6371
        double earthRadius = 6371;
        this.latitude = Math.toRadians(latitude);
        this.longitude = Math.toRadians(longitude);
        this.radius = radius/earthRadius;

        // Set the ranges
        /*this.latMin = latitude - Math.toDegrees(radius/earthRadius);
        this.latMax = latitude + Math.toDegrees(radius/earthRadius);
        this.longMin = longitude - Math.toDegrees(radius/earthRadius/Math.cos(Math.toRadians(latitude)));
        this.longMax = longitude + Math.toDegrees(radius/earthRadius/Math.cos(Math.toRadians(latitude)));*/
        this.latMin = Math.asin(Math.sin(latitude)*Math.cos(radius)+Math.cos(latitude) *Math.sin(radius)*Math.cos(Math.PI));
        this.latMax = Math.asin(Math.sin(latitude)*Math.cos(radius)+Math.cos(latitude) *Math.sin(radius)*Math.cos(0));
        double longMinRadian = longitude + Math.atan2(Math.sin(Math.PI/2)*Math.sin(radius) * Math.cos(latitude), 
                                                                                        Math.cos(radius)-Math.sin(latitude)*Math.sin(latitude));
        longMinRadian = (longMinRadian + 3 * Math.PI) % (2 * Math.PI)- Math.PI;
        this.longMin = Math.toDegrees(longMinRadian);
        double longMaxRadian = longitude + Math.atan2(Math.sin(3*Math.PI/2)*Math.sin(radius) * Math.cos(latitude), 
                                                                                        Math.cos(radius)-Math.sin(latitude)*Math.sin(latitude));
        longMaxRadian = (longMaxRadian + 3 * Math.PI) % (2 * Math.PI)- Math.PI;
        this.longMax = Math.toDegrees(longMaxRadian);
    }
}
