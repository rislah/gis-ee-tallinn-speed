package com.rislah.gis.csv;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class Routes {
    private final RoutesReader routesReader;
    @Getter
    private List<RoutesSchema> routes;

    @Autowired
    public Routes(RoutesReader routesReader) {
        this.routesReader = routesReader;
        init();
    }

    private void init() {
        try {
            this.routes = routesReader.readCsv();
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    public Optional<RoutesSchema> findByRouteNum(String routeNum) {
        for (RoutesSchema routesSchema : routes) {
            if (routesSchema.getRouteNum() != null && routesSchema.getRouteNum().equals(routeNum)) {
                return Optional.of(routesSchema);
            }
        }
        return Optional.empty();
    }
}
