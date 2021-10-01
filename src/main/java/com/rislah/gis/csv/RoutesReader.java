package com.rislah.gis.csv;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

@Component
public class RoutesReader {
    private static final URI ROUTES = URI.create("https://transport.tallinn.ee/data/routes.txt");
    private final HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();

    public List<RoutesSchema> readCsv() throws IOException, InterruptedException {
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = csvMapper.schemaFor(RoutesSchema.class).withColumnSeparator(';').withHeader();
        HttpRequest request = createRequest();
        HttpResponse<String> response = sendRequest(request);
        MappingIterator<RoutesSchema> it = csvMapper
                .readerFor(RoutesSchema.class)
                .with(csvSchema)
                .readValues(response.body());
        return it.readAll();
    }

    private HttpRequest createRequest() {
        return HttpRequest.newBuilder()
                .GET()
                .uri(ROUTES)
                .build();
    }

    private HttpResponse<String> sendRequest(HttpRequest request) throws IOException, InterruptedException {
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
