package com.rislah.gis.geodata;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Component
public class GeoDataHTTP {
    public static final URI GEO_URI = URI.create("https://gis.ee/tallinn/geojson.php");
    private static final int TIMEOUT = 5;

    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(TIMEOUT))
            .build();

    public HttpResponse<String> sendRequest(HttpRequest request) throws IOException, InterruptedException {
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public HttpRequest prepareRequest() {
        return HttpRequest.newBuilder()
                .GET()
                .uri(GEO_URI)
                .timeout(Duration.ofSeconds(TIMEOUT))
                .build();
    }
}
