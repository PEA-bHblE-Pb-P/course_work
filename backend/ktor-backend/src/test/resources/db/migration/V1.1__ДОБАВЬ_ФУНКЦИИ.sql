CREATE OR REPLACE FUNCTION verify_character_exists(character_id int) RETURNS VOID AS
$$
BEGIN
    if NOT EXISTS(SELECT * FROM "character" WHERE "id" = character_id) THEN
        RAISE EXCEPTION 'Персонажа с id = % нет.', character_id;
    END IF;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION verify_character_alive(character_id int) RETURNS VOID AS
$$
BEGIN
    if (SELECT location_id FROM "character" WHERE id = character_id) IS NULL THEN
        RAISE EXCEPTION 'Персонаж с id = % мёртв.', character_id;
    END IF;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION equals_locations(char_id int, char_id2 int) RETURNS BOOLEAN AS
$$
BEGIN
    return (SELECT location_id FROM "character" WHERE id = char_id) =
           (SELECT location_id FROM "character" WHERE id = char_id2);
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION go_to_location_by_id(character_id int, loc_id int) RETURNS VOID AS
$$
DECLARE
    loc_name varchar;
BEGIN
    PERFORM verify_character_exists(character_id);
    SELECT name FROM "location" WHERE id = loc_id INTO loc_name;
    IF (SELECT c.location_id FROM "character" c WHERE id = character_id) = loc_id THEN
        RAISE NOTICE 'Персонаж уже в %', loc_name;
    ELSE
        UPDATE "character" SET location_id = loc_id WHERE id = character_id;
        INSERT INTO "location_history" VALUES (character_id, loc_id);
        RAISE NOTICE 'Персонаж id = % отправился в %', character_id, loc_name;
    end if;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION go_to_location(character_id int, location_name varchar) RETURNS VOID AS
$$
DECLARE
    loc_id int = 0;
BEGIN
    SELECT id FROM "location" WHERE name LIKE location_name INTO loc_id;
    if loc_id != 0 THEN
        PERFORM go_to_location_by_id(character_id, loc_id);
    else
        RAISE EXCEPTION 'Места с именем "%" нет.', location_name;
    end if;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION go_to_servant_by_id(character_id int, servant_id int) RETURNS VOID AS
$$
DECLARE
    loc_id int; DECLARE loc_name varchar;
BEGIN
    PERFORM verify_character_exists(servant_id);
    SELECT location_id, location.name
    FROM "character" c
             JOIN "location" ON location_id = location.id
    WHERE c.id = servant_id
    INTO loc_id, loc_name;
    PERFORM go_to_location(character_id, loc_name);
END;
$$ language plpgsql;

CREATE OR REPLACE FUNCTION character_type_nearby(character_id int, character_type_id int)
    RETURNS TABLE
            (
                id      int,
                name    varchar,
                sex     varchar,
                type_id int
            )
AS
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
$$ language plpgsql;


CREATE OR REPLACE FUNCTION people_nearby(character_id int)
    RETURNS TABLE
            (
                id      int,
                name    varchar,
                sex     varchar,
                type_id int,
                birthday date,
                blood_percentage int
            )
AS
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
        SELECT c.id AS id, c.name AS name, sx.name AS sex, c.type_id as type_id, c.birthday as birthday, c.blood_percentage as blood_percentage
        FROM "character" c
                 JOIN sex sx ON sx.id = c.sex_id
        WHERE c.location_id = curr_loc_id
          AND c.type_id = human_type_id;
end;
$$ language plpgsql;

CREATE OR REPLACE VIEW bars AS
SELECT *
from location
WHERE location_type_id = ANY (SELECT id FROM location_type WHERE type LIKE '%bar%');

CREATE OR REPLACE FUNCTION go_to_bar(bar_id int, vamp_id int) RETURNS VOID AS
$$
DECLARE
    pass_bar_id     int;
    pass_servant_id int;
BEGIN
    IF (bar_id = ANY (SELECT id FROM bars)) THEN
        PERFORM verify_character_exists(vamp_id);
        SELECT DISTINCT(p.location_id), c.id
        FROM passes p
                 RIGHT JOIN character c on c.id = p.character_id
                 RIGHT JOIN vampire_to_servant vts on c.id = vts.servant_id
        WHERE vts.vampire_id = vamp_id
          AND p.location_id = bar_id
        LIMIT 1
        INTO pass_bar_id, pass_servant_id;
        IF (pass_servant_id IS NOT NULL) THEN
            PERFORM go_to_location_by_id(vamp_id, bar_id);
            PERFORM go_to_location_by_id(pass_servant_id, bar_id);
        ELSE
            RAISE EXCEPTION 'У вампира id=% нет слуги, который имеет доступ в бар id=%', vamp_id, bar_id;
        END IF;
    ELSE
        PERFORM go_to_location_by_id(vamp_id, bar_id);
    end if;
END;
$$ LANGUAGE plpgsql;

create or replace function kill(char_id int, killer_id int, description character varying) returns void
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
$$ language plpgsql;

CREATE OR REPLACE FUNCTION drink_blood(vamp_id int, char_id int, amount int) RETURNS VOID AS
$$
DECLARE
    current_blood_amount int;
    okay_blood_amount    int = 85;
    dead_blood_amount    int = 75;
BEGIN
    assert char_id != vamp_id, 'check killer not eq victim';
    PERFORM verify_character_exists(vamp_id);
    PERFORM verify_character_exists(char_id);
    assert (select type_id from character where id = vamp_id) !=
           (select id from type where name = 'человек'), 'check not human';

    SELECT blood_percentage FROM character WHERE id = char_id INTO current_blood_amount;
    IF (current_blood_amount >= amount) THEN
        SELECT (current_blood_amount - amount) INTO current_blood_amount;
        INSERT INTO drink_blood(blood_amount_percentage, character_id, drinker_id) VALUES (amount, char_id, vamp_id);
        UPDATE character SET blood_percentage = current_blood_amount WHERE id = char_id;
        RAISE NOTICE 'Вампир id=% выпил кровь у персонажа id=%', vamp_id, char_id;
        IF (current_blood_amount <= dead_blood_amount) THEN
            perform kill(char_id, vamp_id, 'Убийством посредством испития крови');
            RAISE NOTICE 'Вероятно персонажу id=% стоит отправится на кладбище, остаётся надеяться, что его заметят другие.', char_id;
        ELSEIF (current_blood_amount <= okay_blood_amount) THEN
            RAISE NOTICE 'Персонажу id=% немного поплохело.', char_id;
        END IF;
    ELSE
        RAISE EXCEPTION 'Jesus... You want to drink blood too much...';
    end if;
END ;
$$ language plpgsql;


create or replace function hunter_go_to_for_fight(hunter_id integer, target_location_id integer) returns void
    language plpgsql
as
$$
declare
    vampire_type          integer;
    vampire_cnt           integer;
    vamp_blood_percentage integer;
begin
    perform verify_character_exists(hunter_id);
    perform verify_character_alive(hunter_id);
    assert (select type_id from character where id = hunter_id) =
           (select id from type where name = 'охотник на вампиров'), 'check hunter type';
    RAISE NOTICE 'Охотник на вамиров id=% вышел на охоту', hunter_id;

    perform go_to_location_by_id(hunter_id, target_location_id);
    select id from type where name = 'вампир' INTO vampire_type;
    select COUNT(*) from character_type_nearby(hunter_id, 1) into vampire_cnt;
    RAISE NOTICE 'Вампиров рядом: %', vampire_cnt;
    if vampire_cnt > 1 then
        perform kill(hunter_id, (select id from character_type_nearby(hunter_id, 1) LIMIT 1),
                     'Охотник проиграл вампиру и умер');
        RAISE NOTICE 'Охотник проиграл вампиру и умер';
    elseif vampire_cnt = 1 then
        select blood_percentage
        from character c
        where c.id = (select id from character_type_nearby(hunter_id, 1))
        into vamp_blood_percentage;
        if vamp_blood_percentage != 100 then
            perform kill((select id from character_type_nearby(hunter_id, 1) LIMIT 1), hunter_id,
                         'Вампир слаб и его убил охотник');
            RAISE NOTICE 'Вампир слаб и его убил охотник';
        else
            perform kill(hunter_id, (select id from character_type_nearby(hunter_id, 1) LIMIT 1),
                         'Вампир силён и убил охотника');
            RAISE NOTICE 'Вампир силён и убил охотника';
        end if;
    else
        RAISE NOTICE 'Охотник не нашёл вампиров';
    end if;

end ;
$$;
