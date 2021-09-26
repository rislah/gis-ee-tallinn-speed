package com.rislah.gis.geodata;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(properties = {
        "spring.flyway.enabled=false",
        "spring.jpa.hibernate.ddl-auto=none",
        "spring.main.lazy-initialization=true"
})
@ExtendWith(MockitoExtension.class)
public class GeoDataSchedulerTest {
    @Autowired
    private GeoDataScheduler geoDataScheduler;

    @SpyBean
    private GeoDataService geoDataService;

    @MockBean
    private GeoDataRepository geoDataRepository;

    @MockBean
    private GeoDataHTTP geoDataHTTP;

    @Captor
    private ArgumentCaptor<List<GeoData>> saveAllArgumentCaptor;

    @Captor
    private ArgumentCaptor<HttpRequest> sendRequestArgumentCaptor;

    @Mock
    private HttpResponse<String> httpResponseMock;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFetchAndSave() throws IOException, InterruptedException {
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
        when(geoDataHTTP.sendRequest(sendRequestArgumentCaptor.capture())).thenReturn(httpResponseMock);
        geoDataScheduler.fetchAndSaveData();

        verify(geoDataService).saveAllList(saveAllArgumentCaptor.capture());
        List<GeoData> geoDataList = saveAllArgumentCaptor.getValue();
        assertThat(geoDataList.size(), is(1));

        GeometryFactory geometryFactory = new GeometryFactory();
        Point point = geometryFactory.createPoint(new Coordinate(24.75408, 59.4369, 0));

        assertEquals(geoDataList.get(0).getPoint(), point);
    }
}
