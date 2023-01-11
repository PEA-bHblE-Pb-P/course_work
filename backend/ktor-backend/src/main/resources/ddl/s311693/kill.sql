create or replace function s311693.kill(char_id integer, killer_id integer, description character varying) returns void
    language plpgsql
as
$$
begin
    assert char_id != killer_id, 'check killer not eq victim';
    perform verify_character_alive(char_id);
    perform verify_character_alive(killer_id);
    assert equals_locations(char_id, killer_id), 'check killer and victim have some locations';

    INSERT INTO murder(KILLER_ID, VICTIM, DESCRIPTION, date)
    VALUES (killer_id, char_id, description, NOW());
    UPDATE character SET location_id = null, place_of_living_id = null WHERE id = char_id;
    RAISE NOTICE 'id=% убил id=%', killer_id, char_id;
end;
$$;

alter function s311693.kill(integer, integer, varchar) owner to s311693;

