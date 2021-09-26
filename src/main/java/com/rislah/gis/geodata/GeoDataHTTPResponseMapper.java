package com.rislah.gis.geodata;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Component
public class GeoDataHTTPResponseMapper {
    private final GeoDataMapper geoDataMapper;
    private final ObjectMapper objectMapper;

    @Autowired
    public GeoDataHTTPResponseMapper(GeoDataMapper geoDataMapper, ObjectMapper objectMapper) {
        this.geoDataMapper = geoDataMapper;
        this.objectMapper = objectMapper;
    }

    public List<GeoDataDTO> mapResponseToDto(HttpResponse<String> res) throws JsonProcessingException {
        GeoFeatureCollection geoFeatureCollection = objectMapper.readValue(res.body(), GeoFeatureCollection.class);
        List<GeoDataDTO> geoDataDTOList = new ArrayList<>();
        geoFeatureCollection.forEach(feature -> {
            geoDataDTOList.add(geoDataMapper.featureToGeoDataDTO(feature, geoFeatureCollection.getTimestamp()));
        });
        return geoDataDTOList;
    }
}
