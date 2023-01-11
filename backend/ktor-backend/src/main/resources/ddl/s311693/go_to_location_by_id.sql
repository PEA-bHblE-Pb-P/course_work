create or replace function s311693.go_to_location_by_id(character_id integer, loc_id integer) returns void
    language plpgsql
as
$$
DECLARE
    loc_name varchar;
BEGIN
    PERFORM verify_character_exists(character_id);
    SELECT name FROM "location" WHERE id = loc_id INTO loc_name;
    IF (SELECT c.location_id FROM "character" c WHERE id = character_id) = loc_id THEN
        RAISE NOTICE 'Персонаж уже в %', loc_name;
    ELSE
        UPDATE "character" SET location_id = loc_id WHERE id = character_id;
        INSERT INTO "location_history" VALUES (character_id, loc_id);
        RAISE NOTICE 'Персонаж id = % отправился в %', character_id, loc_name;
    end if;
END;
$$;

alter function s311693.go_to_location_by_id(integer, integer) owner to s311693;

