DROP TRIGGER IF EXISTS age_trigger ON favor;
DROP TRIGGER IF EXISTS very_hard_trigger ON character;

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
CHECK (birthday < CURRENT_TIMESTAMP)