DROP TRIGGER IF EXISTS age_trigger ON favor;
DROP TRIGGER IF EXISTS very_hard_trigger ON character;

CREATE OR REPLACE FUNCTION validateAge() RETURNS TRIGGER AS $BODY$
BEGIN
    IF new.age_min >= 18 AND new.age_min <= new.age_max THEN
        RETURN NEW;
    ELSE
        RAISE EXCEPTION 'Возрастные границы указаны неверно';
    END IF;
END;
$BODY$ LANGUAGE plpgsql;

CREATE TRIGGER age_trigger BEFORE INSERT OR UPDATE ON favor
FOR EACH ROW EXECUTE PROCEDURE validateAge();

ALTER TABLE character ADD CONSTRAINT verifyBirthday
CHECK (birthday < CURRENT_TIMESTAMP)