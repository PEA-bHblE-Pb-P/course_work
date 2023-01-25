create or replace function s311693.people_nearby(character_id integer)
    returns TABLE(id integer, name character varying, sex character varying, type_id integer, birthday date, blood_percentage integer)
    language plpgsql
as
$$
    # variable_conflict use_column
DECLARE
    curr_loc_id   int;
    human_type_id int;
BEGIN
    PERFORM verify_character_exists(character_id);
    SELECT location_id FROM "character" c WHERE c.id = character_id INTO curr_loc_id;
    SELECT type.id FROM "type" WHERE name LIKE 'человек' INTO human_type_id;

    RETURN QUERY
        SELECT c.id               AS id,
               c.name             AS name,
               sx.name            AS sex,
               c.type_id          as type_id,
               c.birthday         as birthday,
               c.blood_percentage as blood_percentage
        FROM "character" c
                 JOIN sex sx ON sx.id = c.sex_id
        WHERE c.location_id = curr_loc_id
          AND c.type_id = human_type_id;
end;
$$;

alter function s311693.people_nearby(integer) owner to s311693;

