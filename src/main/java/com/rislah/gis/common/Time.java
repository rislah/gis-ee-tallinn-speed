package com.rislah.gis.common;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Time {
    private static final ZoneId zoneId = ZoneId.of("Europe/Tallinn");
    public static ZonedDateTime zonedDateTimeFromEpochSeconds(long seconds) {
        Instant epochSecond = Instant.ofEpochSecond(seconds);
        ZoneId zone = ZoneId.of("Europe/Tallinn");
        return epochSecond.atZone(zone);
    }
}
