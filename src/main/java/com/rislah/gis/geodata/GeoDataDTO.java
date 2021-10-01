package com.rislah.gis.geodata;

import lombok.Builder;
import lombok.Data;
import org.locationtech.jts.geom.Point;

import java.time.ZonedDateTime;

@Builder
@Data
public class GeoDataDTO {
    private Point point;
    private int vehicleTypeId;
    private int vehicleId;
    private String route;
    private ZonedDateTime timestamp;
}
