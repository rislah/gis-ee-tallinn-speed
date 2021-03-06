package com.rislah.gis.geodata;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class GeoDataScheduler {
    private static final int FIXED_RATE_SECONDS = 5;

    private final GeoDataMapper geoDataMapper;
    private final GeoDataHTTP geoDataHTTP;
    private final GeoDataHTTPResponseMapper geoDataHTTPResponseMapper;
    private final GeoDataService geoDataService;
    private final Timer timer;

    @Autowired
    public GeoDataScheduler(GeoDataMapper geoDataMapper, GeoDataHTTP geoDataHTTP, GeoDataHTTPResponseMapper
            geoDataHTTPResponseMapper, GeoDataService geoDataService, MeterRegistry meterRegistry) {
        this.geoDataMapper = geoDataMapper;
        this.geoDataHTTP = geoDataHTTP;
        this.geoDataHTTPResponseMapper = geoDataHTTPResponseMapper;
        this.geoDataService = geoDataService;
        timer = meterRegistry.timer("geodata_scheduler_requests", "method", "GET");
    }

    @Scheduled(fixedRate = FIXED_RATE_SECONDS, timeUnit = TimeUnit.SECONDS)
    public void fetchAndSaveData() {
        HttpResponse<String> res = timer.record(() -> {
            try {
                HttpRequest request = geoDataHTTP.prepareRequest();
                return geoDataHTTP.sendRequest(request);
            } catch (Exception ex) {
                log.error(ex.toString());
            }
            return null;
        });

        if (res == null) {
            return;
        }

        try {
            List<GeoDataDTO> geoDataDTOS = geoDataHTTPResponseMapper.mapResponseToDto(res);
            List<GeoData> geoData = geoDataDTOS.stream().map(geoDataMapper::geoDataDTOToGeoData).toList();
            if (geoData.size() > 0) {
                geoDataService.saveAllList(geoData);
            }
            log.info("Saved {} entries", geoData.size());
        } catch (JsonProcessingException ex) {
            log.error(ex.toString());
        }
    }
}
