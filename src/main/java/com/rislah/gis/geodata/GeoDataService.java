package com.rislah.gis.geodata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeoDataService {
    private final GeoDataRepository repository;

    @Autowired
    public GeoDataService(GeoDataRepository repository) {
        this.repository = repository;
    }

    public void saveAllList(List<GeoData> geoDataList) {
        repository.saveAll(geoDataList);
    }
}
