create table s311693.drink_blood
(
    id                      serial
        primary key,
    blood_amount_percentage integer,
    character_id            integer
        references s311693.character,
    drinker_id              integer
        references s311693.character,
    drink_time              timestamp default now() not null
);

alter table s311693.drink_blood
    owner to s311693;

create or replace function s311693.drink_blood(vamp_id integer, char_id integer, amount integer) returns void
    language plpgsql
as
$$
DECLARE
    current_blood_amount int;
    okay_blood_amount    int = 85;
    dead_blood_amount    int = 75;
BEGIN
    PERFORM verify_character_exists(vamp_id);
    PERFORM verify_character_exists(char_id);
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
END;
$$;

alter function s311693.drink_blood(integer, integer, integer) owner to s311693;

