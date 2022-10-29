CREATE OR REPLACE FUNCTION verifyCharacterExists(character_id int) RETURNS VOID AS $$
    BEGIN
        if NOT EXISTS(SELECT * FROM character WHERE id = character_id) THEN
            RAISE EXCEPTION 'Персонажа с id = % нет.', character_id;
        END IF;
    END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION goToLocation(character_id int, location_name varchar) RETURNS VOID AS $$
DECLARE loc_id int = 0;
DECLARE loc_name varchar;
    BEGIN
        PERFORM verifyCharacterExists(character_id);
        SELECT id, name FROM location WHERE name LIKE location_name INTO loc_id, loc_name;
        if loc_id != 0 THEN
            IF (SELECT location_id FROM character WHERE id = character_id) = loc_id THEN
                RAISE NOTICE 'Персонаж уже в %', loc_name;
            ELSE
                UPDATE character SET location_id = loc_id WHERE id = character_id;
                INSERT INTO location_history VALUES (character_id, loc_id);
                RAISE NOTICE 'Персонаж id = % отправился в %', character_id, loc_name;
            end if;
        else
            RAISE EXCEPTION 'Места с именем "%" нет.', location_name;
        end if;
END;
$$ LANGUAGE plpgsql;

SELECT goToLocation(1, 'Genghis Khan Hous_');

CREATE OR REPLACE FUNCTION goToServantById(character_id int, servant_id int) RETURNS VOID AS $$
    DECLARE loc_id int; DECLARE loc_name varchar;
    BEGIN
        PERFORM verifyCharacterExists(servant_id);
        SELECT location_id, location.name FROM character c
            JOIN location ON location_id = location.id WHERE c.id = servant_id INTO loc_id, loc_name;
        PERFORM goToLocation(character_id, loc_name);
    END;
$$ language plpgsql;

SELECT goToServantById(1, 5);
