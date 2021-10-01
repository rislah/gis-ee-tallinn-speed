package com.rislah.gis.geodata;


import com.rislah.gis.common.Time;
import com.rislah.gis.common.validator.TimeConstraint;
import com.rislah.gis.csv.Routes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
@RestController
@Validated
public class GeoDataController {
    private final GeoDataSpeedViewRepository speedViewRepository;
    private final GeoDataSpeedMapper mapper;

    @Autowired
    public GeoDataController(GeoDataSpeedViewRepository speedViewRepository, GeoDataSpeedMapper mapper, Routes csvReader) {
        this.speedViewRepository = speedViewRepository;
        this.mapper = mapper;
    }

    @GetMapping(path = "/speed", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GeoDataSpeedDTO> findAll(
            @RequestParam(name = "time", required = false)
            @TimeConstraint Long epochSeconds
    ) {
        if (epochSeconds != null) {
            ZonedDateTime time = Time.zonedDateTimeFromEpochSeconds(epochSeconds);
            return speedViewRepository.findAllByTimestamp(time).stream().map(mapper::speedViewToSpeedDTO).toList();
        }
        return speedViewRepository.findAll().stream().map(mapper::speedViewToSpeedDTO).toList();
    }
}
