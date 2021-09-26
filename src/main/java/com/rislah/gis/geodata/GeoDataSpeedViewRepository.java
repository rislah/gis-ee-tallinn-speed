package com.rislah.gis.geodata;

import com.rislah.gis.common.ReadOnlyRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeoDataSpeedViewRepository extends ReadOnlyRepository<GeoDataSpeedView, Integer> {
}