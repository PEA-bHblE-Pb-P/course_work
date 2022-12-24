create view s311693.bars(id, lat, lon, name, location_type_id) as
SELECT location.id,
       location.lat,
       location.lon,
       location.name,
       location.location_type_id
FROM location
WHERE (location.location_type_id IN (SELECT location_type.id
                                     FROM location_type
                                     WHERE location_type.type::text ~~ '%bar%'::text));

alter table s311693.bars
    owner to s311693;

