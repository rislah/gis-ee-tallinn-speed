package com.rislah.gis.geodata;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "gdata", indexes = {
        @Index(name = "idx_geodata_timestamp", columnList = "timestamp"),
        @Index(name = "idx_geodata_vehicle_id_id", columnList = "vehicle_id, id")
})
@Getter
@Setter
@NoArgsConstructor
public class GeoData {
    @Id
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gdata_generator")
    @SequenceGenerator(name = "gdata_generator", sequenceName = "gdata_id_seq", allocationSize = 1)
    private int id;

    @Column(name = "point", columnDefinition = "geometry(Point,4326)", nullable = false)
    private Point point;

    @Column(name = "timestamp", nullable = false, columnDefinition = "timestamptz")
    private ZonedDateTime timestamp;

    @Column(name = "vehicle_id", nullable = false, columnDefinition = "int")
    private int vehicleId;

    @Column(name = "vehicle_type", nullable = false, columnDefinition = "int")
    private int vehicleTypeId;

    @Column(name = "route", nullable = false, columnDefinition = "text")
    private String route;
}
