create or replace function s311693.validateage() returns trigger
    language plpgsql
as
$$
BEGIN
    IF new.age_min < 18 THEN
        RAISE EXCEPTION 'Можно предпочитать только персонажей, которым строго не меньше 18 лет';
    ELSEIF new.age_min > new.age_max THEN
        RAISE EXCEPTION 'Возрастные границы указаны неверно % > %.', new.age_min, new.age_max;
    ELSE
        RETURN NEW;
    END IF;
END;
$$;

alter function s311693.validateage() owner to s311693;

