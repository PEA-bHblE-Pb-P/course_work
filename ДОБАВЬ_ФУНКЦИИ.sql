CREATE OR REPLACE FUNCTION goToLocation(character_id int, location_name varchar) RETURNS BOOLEAN AS $$
DECLARE loc_id int = 0;
DECLARE loc_name varchar;
    BEGIN
    if EXISTS(SELECT * FROM character WHERE id = character_id) THEN
        SELECT id, name FROM location WHERE name LIKE location_name INTO loc_id, loc_name;
        if loc_id != 0 THEN
            IF (SELECT location_id FROM character WHERE id = character_id) = loc_id THEN
                RAISE NOTICE 'Персонаж уже в %', loc_name;
            ELSE
                UPDATE character SET location_id = loc_id WHERE id = character_id;
                INSERT INTO location_history VALUES (character_id, loc_id);
                RAISE NOTICE 'Персонаж id = % отправился в %', character_id, loc_name;
            end if;
            RETURN TRUE;
        else
            RAISE EXCEPTION 'Места с именем "%" нет.', location_name;
        end if;
    else
        RAISE EXCEPTION 'Персонажа с id = % нет.', character_id;
    end if;
END;
$$ LANGUAGE plpgsql;

SELECT goToLocation(1, 'Genghis Khan Hous_');

