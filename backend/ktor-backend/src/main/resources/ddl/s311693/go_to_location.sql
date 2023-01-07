create or replace function s311693.go_to_location(character_id integer, location_name character varying) returns void
    language plpgsql
as
$$
DECLARE
    loc_id int = 0;
BEGIN
    SELECT id FROM "location" WHERE name LIKE location_name INTO loc_id;
    if loc_id != 0 THEN
        PERFORM go_to_location_by_id(character_id, loc_id);
    else
        RAISE EXCEPTION 'Места с именем "%" нет.', location_name;
    end if;
END;
$$;

alter function s311693.go_to_location(integer, varchar) owner to s311693;

