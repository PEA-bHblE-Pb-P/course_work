create or replace function s311693.verify_character_exists(character_id integer) returns void
    language plpgsql
as
$$
BEGIN
    if NOT EXISTS(SELECT * FROM "character" WHERE "id" = character_id) THEN
        RAISE EXCEPTION 'Персонажа с id = % нет.', character_id;
    END IF;
END;
$$;

alter function s311693.verify_character_exists(integer) owner to s311693;

