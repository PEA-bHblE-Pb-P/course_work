CREATE OR REPLACE FUNCTION validateAge() RETURNS TRIGGER AS
$BODY$
BEGIN
    IF new.age_min < 18 THEN
        RAISE EXCEPTION 'Можно предпочитать только персонажей, которым строго не меньше 18 лет';
    ELSEIF new.age_min <= new.age_max THEN
        RAISE EXCEPTION 'Возрастные границы указаны неверно.';
    ELSE
        RETURN NEW;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER age_trigger
    BEFORE INSERT OR UPDATE
    ON favor
    FOR EACH ROW
EXECUTE PROCEDURE validateAge();

ALTER TABLE character
    ADD CONSTRAINT verifyBirthday
        CHECK (birthday < CURRENT_TIMESTAMP);

CREATE OR REPLACE FUNCTION do_something_if_blood_amount_is_nok() RETURNS TRIGGER AS
$$
DECLARE
    okay_blood_amount      int = 85;
    dead_blood_amount      int = 75;
    hospital_id            int;
    hospital_location_type int;
    drinker_id             int;
    last_drinker_time      int;
BEGIN
    select MAX(drink_time) from drink_blood where character_id = NEW.id into last_drinker_time;
    select drinker_id from drink_blood where character_id = NEW.id and drink_time = last_drinker_time into drinker_id;
    IF (NEW.blood_percentage <= dead_blood_amount) THEN
        perform kill(NEW.id, drinker_id, 'Вампир отсосал всю кровь:(');
        RAISE NOTICE 'Потеря крови фатальная:(';
    ELSEIF (NEW.blood_percentage <= okay_blood_amount) THEN
        IF (NOT (EXISTS(SELECT people_nearby(NEW.id)))) THEN
            perform kill(NEW.id, drinker_id, 'Пытаясь дойти до больницы, умер от потери крови:(');
            RAISE NOTICE 'Пытаясь дойти до больницы, умер от потери крови:(';
        ELSE
            RAISE NOTICE 'Люди вас нашли';
            select id from type where name = 'hospital' INTO hospital_location_type;
            if (select COUNT(*) from location where location_type_id = hospital_location_type LIMIT 1 == 0) THEN
                RAISE NOTICE 'А лечить-то негде. Смерть:(';
                perform kill(NEW.id, drinker_id, 'Убит недостаточным развитием здравоохранения');
            else
                select location_id
                from location
                where location_type_id = hospital_location_type
                LIMIT 1
                into hospital_id;
                RAISE NOTICE 'Человек доставлен в госпиталь';
                perform go_to_location_by_id(NEW.id, hospital_id);
            end if;
        end if;
    ELSE
        IF (NOT (EXISTS(SELECT people_nearby(NEW.id)))) THEN
            RAISE NOTICE 'Людей рядом не оказалось.';
        ELSE
            RAISE NOTICE 'Никто ничего не заметил.';
        end if;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER blood_amount_trigger
    AFTER UPDATE OF blood_percentage
    ON character
    FOR EACH ROW
EXECUTE PROCEDURE do_something_if_blood_amount_is_nok();

-- триггер на убийство вампира
create function execution_cannot_be_pardoned(char_id int) returns void
as
$$
DECLARE
    vampire_type int;
    hunter_type  int;
    vamp_group_id int;
    home_id int;

begin
    if ((select location_id from character where id = char_id) is not null) THEN
        raise NOTICE 'не убийство';
    else
        select id from type where name = 'вампир' INTO vampire_type;
        if ((select type_id from character where id = char_id) != vampire_type) THEN
            raise NOTICE 'суда не будет. Мир несправдлив';
        else
--         вампира убил человек?
            SELECT type.id FROM type WHERE name LIKE 'охотник на вампиров' INTO hunter_type;
            if ((select type_id from character where id = char_id) != hunter_type) then
                raise NOTICE 'просто не повезло. Судить некого';
            else
--         найти группу вампира
            select 'group' from character where id=char_id into vamp_group_id;
            select place_of_living_id from character where id = (select admin_id from 'group' where id = vamp_group_id) into home_id;
--         переместить вампиров в дом группы

--         кто из вампиров группы пересекался с убийцей?
--         если убийство в доме группы и кто-то встречался с убийцей -- осудить и выгнать
--         INSERT INTO court VALUES ();
            end if;
        end if;
    end if;
end;
$$;

CREATE OR REPLACE TRIGGER blood_amount_trigger
    AFTER UPDATE OF location_id
    ON character
    FOR EACH ROW
EXECUTE PROCEDURE execution_cannot_be_pardoned();