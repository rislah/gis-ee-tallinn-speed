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

CREATE INDEX idx_gdata_timestamp ON gdata (timestamp);
CREATE INDEX idx_vehicleid_id_idx ON gdata (vehicle_id, id);

CREATE OR REPLACE VIEW v_gdata_speed AS
WITH cte AS
         (
             SELECT DISTINCT ON (vehicle_id) id,
                                             vehicle_id,
                                             route,
                                             vehicle_type,
                                             point,
                                             LAG(point) OVER (PARTITION BY vehicle_id ORDER BY id) AS point_before,
                                             timestamp
             FROM gdata
             ORDER BY vehicle_id, id DESC
         )

SELECT c.vehicle_id,
       c.distance_meters,
       c.vehicle_type,
       v.name                                         AS vehicle_type_name,
       c.route,
       c.timestamp                                    AS tz,
       round(calculate_kmh(c.distance_meters, 10), 2) AS kmh
FROM (
         SELECT route,
                vehicle_id,
                vehicle_type,
                round(ST_DistanceSphere(point, point_before)::numeric, 2) AS distance_meters,
                timestamp
         FROM cte
         WHERE point_before IS NOT NULL
     ) AS c
         JOIN vehicle v ON c.vehicle_type = v.id
ORDER BY kmh DESC;

CREATE OR REPLACE FUNCTION fn_speed_from_time(p_fromtime timestamptz)
    returns TABLE
            (
                vehicle_id        integer,
                vehicle_type      integer,
                vehicle_type_name text,
                route             text,
                distance_meters   double precision,
                kmh               integer,
                tz                timestamptz
            )
AS
$body$
SELECT c.vehicle_id,
       c.vehicle_type,
       c.name,
       c.route,
       round(ST_DistanceSphere(c.point, c.point_before)::numeric, 2) AS distance_meters,
       ceil(round(calculate_kmh(ST_DistanceSphere(c.point, c.point_before), 15), 1))::integer AS kmh,
       c.timestamp
FROM (
         SELECT DISTINCT ON (g.vehicle_id) g.vehicle_id,
                                           g.route,
                                           g.vehicle_type,
                                           v.name,
                                           g.timestamp,
                                           g.point,
                                           lag(g.point) over (partition BY g.vehicle_id ORDER BY g.id) AS point_before
         FROM gdata g
                  INNER JOIN vehicle v ON v.id = g.vehicle_type
         where timestamp BETWEEN p_fromtime AND p_fromtime + 15 * interval '1 second'
         ORDER BY g.vehicle_id, g.id DESC
     ) AS c
WHERE c.point_before IS NOT NULL
ORDER BY kmh DESC;
$body$
    language sql;



--
-- SELECT c.*, round(calculate_kmh(c.distance_meters, 15), 2) AS kmh
-- FROM (
--          SELECT id,
--                 vehicle_id,
--                 round(ST_DistanceSphere(cte.point, cte.point_before)::numeric, 2) AS distance_meters,
--                 cte.timestamp
--          FROM cte
--          WHERE cte.point_before IS NOT NULL
--      ) AS c
-- ORDER BY kmh DESC;
--
--     WITH cte AS (
--     SELECT id, vehicle_id, point, LAG(point, 1) OVER (PARTITION BY vehicle_id ORDER BY id DESC ) as point_before, timestamp FROM gdata ORDER BY vehicle_id, timestamp ASC
-- )
--
-- SELECT c.vehicle_id, round(avg(calculate_kmh(c.distance_meters, 15)), 2) as kmh
-- FROM (
--          SELECT id, vehicle_id, round(ST_DistanceSphere(cte.point, cte.point_before)::numeric, 2) AS distance_meters, cte.timestamp
--          FROM cte
--          WHERE cte.point_before IS NOT NULL
--      ) AS c
-- GROUP BY c.vehicle_id
-- ORDER BY kmh DESC;
