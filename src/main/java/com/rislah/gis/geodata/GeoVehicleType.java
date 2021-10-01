package com.rislah.gis.geodata;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class GeoVehicleType {
    private int id;
    private String name;
}
