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
BEGIN
    IF (NEW.blood_percentage <= dead_blood_amount) THEN
        RAISE NOTICE 'Потеря крови фатальная:(';
    ELSEIF (NEW.blood_percentage <= okay_blood_amount) THEN
        IF (NOT (EXISTS(SELECT people_nearby(NEW.id)))) THEN
            RAISE NOTICE 'Пытаясь дойти до больницы, умер от потери крови:(';
        ELSE
            RAISE NOTICE 'Люди вас нашли';
            select id from type where name = 'hospital' INTO hospital_location_type;
            if (select COUNT(*) from location where location_type_id = hospital_location_type LIMIT 1 == 0) THEN
                RAISE NOTICE 'А лечить-то негде. Смерть:(';
                perform kill(NEW.id, null, 'Убит недостаточным развитием здравоохранения');
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
create function execution_cannot_be_pardoned(char_id int, court_id int) returns void
as
$$
DECLARE
    vampire_type int;
begin
    select id from type where name = 'вампир' INTO vampire_type;
    if ((select type_id from character where id = char_id) != vampire_type) THEN
        raise NOTICE 'суда не будет. Мир несправдлив';
    else
--         вампира убил человек?
--         найти группу вампира
--         переместить вампиров в дом группы
--         кто из вампиров группы пересекался с убийцей?
--         если убийство в доме группы и кто-то встречался с убийцей -- осудить и выгнать
--         INSERT INTO court VALUES ();
    end if;
end;
$$;

CREATE OR REPLACE TRIGGER blood_amount_trigger
    AFTER UPDATE OF blood_percentage
    ON character
    FOR EACH ROW
EXECUTE PROCEDURE execution_cannot_be_pardoned();