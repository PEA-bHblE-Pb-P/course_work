create or replace function s311693.verify_character_alive(character_id integer) returns void
    language plpgsql
as
$$
BEGIN
    if (SELECT location_id FROM "character" WHERE id = character_id) IS NULL THEN
        RAISE EXCEPTION 'Персонаж с id = % мёртв.', character_id;
    END IF;
END;
$$;

alter function s311693.verify_character_alive(integer) owner to s311693;

