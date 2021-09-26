package com.rislah.gis.geodata;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(properties = {
        "spring.flyway.enabled=false",
        "spring.jpa.hibernate.ddl-auto=none",
        "spring.main.lazy-initialization=true"
})
@ExtendWith(MockitoExtension.class)
public class GeoDataHTTPTest {
    @Mock
    HttpResponse<String> httpResponseMock;
    @Autowired
    private GeoDataHTTP geoDataHTTP;
    @Autowired
    private GeoDataHTTPResponseMapper geoDataHTTPResponseMapper;
    @MockBean
    private GeoDataRepository repository;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPrepareRequest_returnsRequest() {
        assertThat(geoDataHTTP.prepareRequest(), instanceOf(HttpRequest.class));
    }

    @Test
    public void testMapResponseToDto_maps() throws JsonProcessingException {
        when(httpResponseMock.body()).thenReturn("""
                {
                    "type":"FeatureCollection",
                    "timestamp":"2021-09-25T23:02:02+03:00",
                    "features": [
                        {
                            "type":"Feature",
                            "properties":{
                                "id":142,
                                "type":3,
                                "line":3,
                                "direction": 58
                            },
                            "geometry": {
                                "type":"Point",
                                "coordinates":[24.75408,59.4369]
                            }
                        }
                    ]
                }
                                """);
        List<GeoDataDTO> geoDataDTOList = geoDataHTTPResponseMapper.mapResponseToDto(httpResponseMock);
        GeometryFactory geometryFactory = new GeometryFactory();
        Point point = geometryFactory.createPoint(new Coordinate(24.75408, 59.4369, 0));

        assertThat(geoDataDTOList.size(), is(1));
        assertEquals(geoDataDTOList.get(0).getPoint(), point);
    }
}
