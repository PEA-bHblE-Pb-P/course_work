CREATE OR REPLACE FUNCTION validateAge() RETURNS TRIGGER AS $BODY$
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

CREATE TRIGGER age_trigger BEFORE INSERT OR UPDATE ON favor
FOR EACH ROW EXECUTE PROCEDURE validateAge();

ALTER TABLE character ADD CONSTRAINT verifyBirthday
CHECK (birthday < CURRENT_TIMESTAMP);

CREATE OR REPLACE FUNCTION doSomethingIfBloodAmountIsNok() RETURNS TRIGGER AS $$
    DECLARE okay_blood_amount int = 85;
            dead_blood_amount int = 75;
    BEGIN
        IF (NEW.blood_percentage <= dead_blood_amount) THEN
--             думать
        ELSEIF (NEW.blood_percentage <= okay_blood_amount ) THEN
--             думать
        ELSE
            IF (NOT(EXISTS(SELECT peopleNearBy(NEW.id)))) THEN
                RAISE NOTICE 'Людей рядом не оказалось.';
            ELSE
                RAISE NOTICE 'Никто ничего не заметил.';
            end if;
        END IF;
        RETURN NEW;
    END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER blood_amount_trigger AFTER UPDATE OF blood_percentage ON character
FOR EACH ROW EXECUTE PROCEDURE doSomethingIfBloodAmountIsNok();