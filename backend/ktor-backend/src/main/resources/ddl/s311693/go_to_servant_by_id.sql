create or replace function s311693.go_to_servant_by_id(character_id integer, servant_id integer) returns void
    language plpgsql
as
$$
DECLARE
    loc_id int; DECLARE loc_name varchar;
BEGIN
    PERFORM verify_character_exists(servant_id);
    SELECT location_id, location.name
    FROM "character" c
             JOIN "location" ON location_id = location.id
    WHERE c.id = servant_id
    INTO loc_id, loc_name;
    PERFORM go_to_location(character_id, loc_name);
END;
$$;

alter function s311693.go_to_servant_by_id(integer, integer) owner to s311693;

