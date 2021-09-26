package com.rislah.gis.geodata;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GeoDataController {
    private final GeoDataSpeedViewRepository speedViewRepository;

    @Autowired
    public GeoDataController(GeoDataSpeedViewRepository speedViewRepository) {
        this.speedViewRepository = speedViewRepository;
    }

    @GetMapping(path = "/speed", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GeoDataSpeedView> findAll() {
        return speedViewRepository.findAll();
    }
}
