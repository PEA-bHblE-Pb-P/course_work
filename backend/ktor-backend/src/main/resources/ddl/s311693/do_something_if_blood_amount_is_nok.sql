create or replace function s311693.do_something_if_blood_amount_is_nok() returns trigger
    language plpgsql
as
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
$$;

alter function s311693.do_something_if_blood_amount_is_nok() owner to s311693;

