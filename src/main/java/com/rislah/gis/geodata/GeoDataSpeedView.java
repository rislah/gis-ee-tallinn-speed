package com.rislah.gis.geodata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;

@Entity
@Table(name = "v_gdata_speed")
@Immutable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GeoDataSpeedView {
    @Id
    @Column(nullable = false, name = "vehicle_id")
    private int vehicleId;
    @Column(nullable = false, name = "distance_meters", columnDefinition = "numeric")
    private int distanceMeters;
    @Column(nullable = false, name = "kmh", columnDefinition = "numeric")
    private int kmh;
    @Column(nullable = false, name = "timestamp")
    private ZonedDateTime timestamp;
}