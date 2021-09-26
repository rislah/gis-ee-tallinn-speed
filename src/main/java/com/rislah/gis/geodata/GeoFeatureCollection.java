package com.rislah.gis.geodata;

import lombok.Data;
import org.geojson.Feature;

import java.time.ZonedDateTime;
import java.util.Iterator;
import java.util.List;

@Data
public class GeoFeatureCollection implements Iterable<Feature> {
    private ZonedDateTime timestamp;
    private List<Feature> features;

    @Override
    public Iterator<Feature> iterator() {
        return features.iterator();
    }
}
