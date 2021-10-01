package com.rislah.gis.geodata;

import com.rislah.gis.csv.Routes;
import com.rislah.gis.csv.RoutesSchema;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class GeoDataSpeedMapper {
    protected Routes routesReader;

    @Autowired
    public void setRoutesCsvReader(Routes routesReader) {
        this.routesReader = routesReader;
    }

    @Mapping(source = "view", target = "routeProperties")
    public abstract GeoDataSpeedDTO speedViewToSpeedDTO(GeoDataSpeedView view);

    protected RouteProperties viewToRouteProperties(GeoDataSpeedView view) {
        Optional<RoutesSchema> optRoute = routesReader.findByRouteNum(view.getRoute());
        if (optRoute.isPresent()) {
            RoutesSchema routesSchema = optRoute.get();
            RouteProperties target = new RouteProperties();
            target.setAuthority(routesSchema.getAuthority());
            target.setCity(routesSchema.getCity());
            target.setSpecialDates(routesSchema.getSpecialDates());
            target.setOperator(routesSchema.getOperator());
            return target;
        }
        return null;
    }
}
