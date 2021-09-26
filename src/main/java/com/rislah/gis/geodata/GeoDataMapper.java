package com.rislah.gis.geodata;

import org.geojson.Feature;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;

@Mapper(componentModel = "spring", imports = {ZonedDateTime.class, ZoneId.class})
public interface GeoDataMapper {
    @Mapping(source = "feature", target = "point", resultType = Point.class, qualifiedByName = "featureToPoint")
    @Mapping(source = "feature", target = "vehicleId", qualifiedByName = "featureToVehicleId")
    @Mapping(source = "feature", target = "vehicleType", qualifiedByName = "featureToVehicleType")
    @Mapping(source = "timestamp", target = "timestamp")
    GeoDataDTO featureToGeoDataDTO(Feature feature, ZonedDateTime timestamp);

    @Named("featureToPoint")
    default Point featureToPoint(Feature feature) {
        org.geojson.Point point = (org.geojson.Point) feature.getGeometry();
        GeometryFactory geometryFactory = new GeometryFactory();
        return geometryFactory.createPoint(new Coordinate(point.getCoordinates().getLongitude(), point.getCoordinates().getLatitude()));
    }

    @Named("featureToVehicleId")
    default int featureToVehicleId(Feature feature) {
        Map<String, Object> properties = feature.getProperties();
        for (Map.Entry<String, Object> prop : properties.entrySet()) {
            if (prop.getKey().equals("id")) {
                return (int) prop.getValue();
            }
        }
        return 0;
    }

    @Named("featureToVehicleType")
    default int featureToVehicleType(Feature feature) {
        Map<String, Object> properties = feature.getProperties();
        for (Map.Entry<String, Object> prop : properties.entrySet()) {
            if (prop.getKey().equals("type")) {
                return (int) prop.getValue();
            }
        }
        return 0;
    }

//    @Mapping(target = "timestamp", expression =
//            "java(ZonedDateTime.now(ZoneId.of(\"Europe/Tallinn\")))")
    GeoData geoDataDTOToGeoData(GeoDataDTO dto);
}
