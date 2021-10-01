package com.rislah.gis.csv;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Data
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class RoutesSchema {
    private String routeName;
    private String weekdays;
    private String streets;
    private String routeStops;
    private String routeStopsPlatforms;
    @JsonProperty(" RouteNum")
    private String routeNum;
    private String authority;
    private String city;
    private String transport;
    private String operator;
    private String validityPeriods;
    private String specialDates;
    private String routeTag;
    private String routeType;
    private String commercial;
}
