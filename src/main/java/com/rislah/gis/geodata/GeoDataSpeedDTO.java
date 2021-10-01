package com.rislah.gis.geodata;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Builder
@Data
public class GeoDataSpeedDTO {
    private int vehicleId;
    private GeoVehicleType vehicleType;
    private int distanceMeters;
    private int kmh;
    private ZonedDateTime timestamp;
    private String route;
    private RouteProperties routeProperties;
}
