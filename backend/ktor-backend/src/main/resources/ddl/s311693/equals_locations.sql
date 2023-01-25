create or replace function s311693.equals_locations(char_id integer, char_id2 integer) returns boolean
    language plpgsql
as
$$
BEGIN
    return (SELECT location_id FROM "character" WHERE id = char_id) =
           (SELECT location_id FROM "character" WHERE id = char_id2);
END;
$$;

alter function s311693.equals_locations(integer, integer) owner to s311693;

