package com.rislah.gis.geodata;

import com.rislah.gis.common.ReadOnlyRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface GeoDataSpeedViewRepository extends ReadOnlyRepository<GeoDataSpeedView, Integer> {
    List<GeoDataSpeedView> findAllByTimestamp(ZonedDateTime fromTime);
}