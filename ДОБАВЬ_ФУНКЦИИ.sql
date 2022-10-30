CREATE OR REPLACE FUNCTION verifyCharacterExists(character_id int) RETURNS VOID AS $$
    BEGIN
        if NOT EXISTS(SELECT * FROM character WHERE id = character_id) THEN
            RAISE EXCEPTION 'Персонажа с id = % нет.', character_id;
        END IF;
    END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION goToLocationById(character_id int, loc_id int) RETURNS VOID AS $$
    DECLARE loc_name varchar;
BEGIN
    PERFORM verifyCharacterExists(character_id);
    SELECT name FROM location WHERE id = loc_id INTO loc_name;
    IF (SELECT c.location_id FROM character c WHERE id = character_id) = loc_id THEN
            RAISE NOTICE 'Персонаж уже в %', loc_name;
        ELSE
            UPDATE character SET location_id = loc_id WHERE id = character_id;
            INSERT INTO location_history VALUES (character_id, loc_id);
            RAISE NOTICE 'Персонаж id = % отправился в %', character_id, loc_name;
    end if;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION goToLocation(character_id int, location_name varchar) RETURNS VOID AS $$
DECLARE loc_id int = 0;
    BEGIN
        SELECT id FROM location WHERE name LIKE location_name INTO loc_id;
        if loc_id != 0 THEN
            PERFORM goToLocationById(character_id, loc_id);
        else
            RAISE EXCEPTION 'Места с именем "%" нет.', location_name;
        end if;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION goToServantById(character_id int, servant_id int) RETURNS VOID AS $$
    DECLARE loc_id int; DECLARE loc_name varchar;
    BEGIN
        PERFORM verifyCharacterExists(servant_id);
        SELECT location_id, location.name FROM character c
            JOIN location ON location_id = location.id WHERE c.id = servant_id INTO loc_id, loc_name;
        PERFORM goToLocation(character_id, loc_name);
    END;
$$ language plpgsql;

CREATE OR REPLACE FUNCTION peopleNearby(character_id int) RETURNS TABLE(id int, name varchar, sex varchar) AS $$
    #variable_conflict use_column
    DECLARE
        curr_loc_id int;
        human_type_id int;
    BEGIN
        PERFORM verifyCharacterExists(character_id);
        SELECT location_id FROM character c WHERE c.id = character_id INTO curr_loc_id;
        SELECT type.id FROM type WHERE name LIKE 'человек' INTO human_type_id;

        RETURN QUERY
        SELECT c.id AS id, c.name AS name, sx.name AS sex
        FROM character c
        JOIN sex sx ON sx.id = c.sex_id
        WHERE c.location_id = curr_loc_id AND c.type_id = human_type_id;
    end;
$$ language plpgsql;

CREATE OR REPLACE VIEW bars AS
    SELECT * from location WHERE location_type_id = ANY(SELECT id FROM location_type WHERE type LIKE '%bar%');

CREATE OR REPLACE FUNCTION goToBar(bar_id int, vamp_id int) RETURNS VOID AS $$
    DECLARE pass_bar_id int;
            pass_servant_id int;
    BEGIN
        IF (bar_id = ANY(SELECT id FROM bars)) THEN
            PERFORM verifyCharacterExists(vamp_id);
            SELECT DISTINCT(p.location_id), c.id
            FROM passes p
                     RIGHT JOIN character c on c.id = p.character_id
                     RIGHT JOIN vampire_to_servant vts on c.id = vts.servant_id
            WHERE vts.vampire_id = vamp_id AND p.location_id = bar_id
            LIMIT 1
            INTO pass_bar_id, pass_servant_id;
            IF (pass_servant_id IS NOT NULL) THEN
                PERFORM goToLocationById(vamp_id, bar_id);
                PERFORM goToLocationById(pass_servant_id, bar_id);
            ELSE
                RAISE EXCEPTION 'У вампира id=% нет слуги, который имеет доступ в бар id=%', vamp_id, bar_id;
            END IF;
        ELSE
            PERFORM goToLocationById(vamp_id, bar_id);
        end if;
    END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION drinkBlood(vamp_id int, char_id int, amount int) RETURNS VOID AS $$
    DECLARE current_blood_amount int;
            okay_blood_amount int = 85;
            dead_blood_amount int = 75;
    BEGIN
        PERFORM verifyCharacterExists(vamp_id);
        PERFORM verifyCharacterExists(char_id);
        SELECT blood_percentage FROM character WHERE id = char_id INTO current_blood_amount;
        IF (current_blood_amount >= amount) THEN
            SELECT (current_blood_amount - amount) INTO current_blood_amount;
            INSERT INTO drink_blood(blood_amount_percentage, character_id, drinker_id) VALUES (amount, char_id, vamp_id);
            UPDATE character SET blood_percentage = current_blood_amount WHERE id = char_id;
            RAISE NOTICE 'Вампир id=% выпил кровь у персонажа id=%', vamp_id, char_id;
            IF (current_blood_amount <= dead_blood_amount) THEN
                RAISE NOTICE 'Вероятно персонажу id=% стоит отправится на кладбище, остаётся надеяться, что его заметят другие.', char_id ;
            ELSEIF (current_blood_amount <= okay_blood_amount ) THEN
                RAISE NOTICE 'Персонажу id=% немного поплохело.', char_id;
            END IF;
        ELSE
            RAISE EXCEPTION 'Jesus... You want to drink blood too much...';
        end if;
    END;
$$ language plpgsql;