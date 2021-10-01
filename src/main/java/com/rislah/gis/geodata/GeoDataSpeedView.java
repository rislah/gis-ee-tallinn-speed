package com.rislah.gis.geodata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "v_gdata_speed")
@Immutable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@NamedNativeQueries(@NamedNativeQuery(
        name = "GeoDataSpeedView.findAllByTimestamp",
        query = "select * from fn_speed_from_time(:fromTime)",
        resultClass = GeoDataSpeedView.class
))
public class GeoDataSpeedView {
    @Id
    @Column(nullable = false, name = "vehicle_id")
    private int vehicleId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "vehicle_type")),
            @AttributeOverride(name = "name", column = @Column(name = "vehicle_type_name")),
    })
    private GeoVehicleType vehicleType;

    @Column(nullable = false, name = "distance_meters", columnDefinition = "numeric")
    private int distanceMeters;

    @Column(nullable = false, name = "kmh", columnDefinition = "numeric")
    private int kmh;

    @Column(nullable = false, name = "tz")
    private ZonedDateTime timestamp;

    @Column(nullable = false, name = "route", columnDefinition = "text")
    private String route;
}