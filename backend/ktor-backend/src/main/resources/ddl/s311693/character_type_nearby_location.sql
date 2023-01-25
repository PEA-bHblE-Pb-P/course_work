create or replace function s311693.character_type_nearby_location(location_id integer, character_type_id integer)
    returns TABLE(id integer, name character varying, sex character varying, type_id integer, blood_percentage integer)
    language plpgsql
as
$$
    # variable_conflict use_variable
BEGIN
    RETURN QUERY
        SELECT c.id AS id, c.name AS name, sx.name AS sex, c.type_id as type_id, c.blood_percentage as blood_percentage
        FROM "character" c
                 JOIN "sex" sx ON sx.id = c.sex_id
        WHERE c.location_id = location_id
          AND c.type_id = character_type_id;
end;
$$;

alter function s311693.character_type_nearby_location(integer, integer) owner to s311693;

