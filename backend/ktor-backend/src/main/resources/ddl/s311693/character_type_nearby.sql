create or replace function s311693.character_type_nearby(character_id integer, character_type_id integer)
    returns TABLE(id integer, name character varying, sex character varying, type_id integer)
    language plpgsql
as
$$
    # variable_conflict use_column
DECLARE
    curr_loc_id int;
BEGIN
    PERFORM verify_character_exists(character_id);
    SELECT location_id FROM "character" c WHERE c.id = character_id INTO curr_loc_id;

    RETURN QUERY
        SELECT c.id AS id, c.name AS name, sx.name AS sex, c.type_id as type_id
        FROM "character" c
                 JOIN "sex" sx ON sx.id = c.sex_id
        WHERE c.location_id = curr_loc_id
          AND c.type_id = character_type_id;
end;
$$;

alter function s311693.character_type_nearby(integer, integer) owner to s311693;

