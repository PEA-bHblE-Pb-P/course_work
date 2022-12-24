create function s311693.go_to_bar(bar_id integer, vamp_id integer) returns void
    language plpgsql
as
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
$$;

alter function s311693.go_to_bar(integer, integer) owner to s311693;

