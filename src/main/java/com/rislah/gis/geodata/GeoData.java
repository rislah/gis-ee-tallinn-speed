package com.rislah.gis.geodata;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Table(name = "gdata")
@Getter
@Setter
@NoArgsConstructor
public class GeoData {
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gdata_generator")
    @SequenceGenerator(name = "gdata_generator", sequenceName = "gdata_id_seq", allocationSize = 1)
    private int id;

    @Column(name = "point", columnDefinition = "geometry(Point,4326)", nullable = false)
    private Point point;

    @Column(name = "timestamp", nullable = false, columnDefinition = "timestamptz")
    private ZonedDateTime timestamp;

    @Column(name = "vehicle_id", nullable = false, columnDefinition = "INT")
    private int vehicleId;

    @Column(name = "vehicle_type", nullable = false, columnDefinition = "INT")
    private int vehicleType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        GeoData geoData = (GeoData) o;
        return Objects.equals(id, geoData.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
