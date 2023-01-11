create or replace function s311693.execution_cannot_be_pardoned() returns trigger
    language plpgsql
as
$$
DECLARE
    vampire_type  int;
    hunter_type   int;
    _killer_id    int;
    vamp_group_id int;
    home_id       int;
    it            int;
    defen_id      int;
    admin         int;
begin
    if ((select location_id from character where id = NEW.id) is not null) THEN
        raise NOTICE 'не убийство';
    else
        select id from type where name = 'вампир' INTO vampire_type;
        if ((select type_id from character where id = NEW.id) != vampire_type) THEN
            raise NOTICE 'суда не будет. Мир несправдлив';
        else
--         вампира убил человек?
            SELECT type.id FROM type WHERE name LIKE 'охотник на вампиров' INTO hunter_type;
            select "killer_id" from murder where victim = NEW.id into _killer_id;
            if ((select type_id from character where id = _killer_id) != hunter_type) then
                raise NOTICE 'просто не повезло. Судить некого';
            else
--         найти группу вампира
                select "group" from character where id = NEW.id into vamp_group_id;
                select admin_id from "group" where id = vamp_group_id into admin;
                select place_of_living_id
                from character
                where id = admin
                into home_id;
                --         переместить вампиров в дом группы
                for it IN
                    select id from character where "group" = vamp_group_id
                    loop
                        perform go_to_location_by_id(it, home_id);
                    end loop;
                raise notice 'iterated over go';

--          кто из вампиров группы пересекался с убийцей?

                foreach it IN ARRAY (select location_id from location_history where character_id = _killer_id)
                    loop
                        select c_id, location_id, visit_time
                        from ((select id as c_id from character where "group" = vamp_group_id) as ci
                            join location_history lh on c_id = lh.character_id)
                        where location_id = it
                          and EXTRACT(days FROM (now() - visit_time)) <= 7
                        order by visit_time desc
                        limit 1
                        into defen_id;
                        if defen_id is not null then
                            INSERT INTO court(verdict, date, defendant_id, group_id, location_id)
                            VALUES ('Выгнан из группы за встречу с охотником', NOW(), defen_id, vamp_group_id, home_id);
                            UPDATE character SET place_of_living_id = null, "group" = null WHERE id = defen_id;
                            RAISE NOTICE 'id=% выгнан из группы %', defen_id, vamp_group_id;
                            exit;
                        end if;
                    end loop;
                INSERT INTO court(verdict, date, defendant_id, group_id, location_id)
                VALUES ('Не удалось устновить виновника', NOW(), null, vamp_group_id, home_id);
                RAISE NOTICE 'Не удалось устновить виновника';
            end if;
        end if;
    end if;
    RETURN NEW;
end ;
$$;

alter function s311693.execution_cannot_be_pardoned() owner to s311693;

