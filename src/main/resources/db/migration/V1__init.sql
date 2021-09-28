CREATE TABLE gdata
(
    id           SERIAL PRIMARY KEY,
    vehicle_type INT                   NOT NULL,
    vehicle_id   INT                   NOT NULL,
    timestamp    timestamptz           NOT NULL,
    point        geometry(Point, 4326) NOT NULL
);

CREATE OR REPLACE FUNCTION calculate_kmh(distance FLOAT, tm INTEGER) RETURNS NUMERIC AS
$$
DECLARE
    kmh                  NUMERIC;
    DECLARE hour_in_secs INTEGER := 3600;
    DECLARE metres_in_km INTEGER := 1000;
BEGIN
    SELECT ((distance / tm) * hour_in_secs) / metres_in_km INTO kmh;
    RETURN kmh;
END;
$$ LANGUAGE plpgsql;

CREATE INDEX gdata_vehicleid_idx ON gdata (vehicle_id);

CREATE OR REPLACE VIEW v_gdata_speed AS
WITH cte AS
         (
             SELECT DISTINCT ON (vehicle_id) id,
                                             vehicle_id,
                                             point,
                                             LAG(point, 1) OVER (PARTITION BY vehicle_id ORDER BY id) AS point_before,
                                             timestamp
             FROM gdata
             ORDER BY vehicle_id, id DESC
         )

SELECT c.*, round(calculate_kmh(c.distance_meters, 15), 2) AS kmh
FROM (
         SELECT id,
                vehicle_id,
                round(ST_DistanceSphere(cte.point, cte.point_before)::numeric, 2) AS distance_meters,
                cte.timestamp
         FROM cte
         WHERE cte.point_before IS NOT NULL
     ) AS c
ORDER BY kmh DESC;
