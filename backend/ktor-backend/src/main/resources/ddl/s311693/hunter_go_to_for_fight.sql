create or replace function s311693.hunter_go_to_for_fight(hunter_id integer, target_location_id integer) returns void
    language plpgsql
as
$$
declare
    vampire_type          integer;
    vampire_cnt           integer;
    vamp_blood_percentage integer;
begin
    perform verify_character_exists(hunter_id);
    perform verify_character_alive(hunter_id);
    assert (select type_id from character where id = hunter_id) =
           (select id from type where name = 'охотник на вампиров'), 'check hunter type';
    RAISE NOTICE 'Охотник на вампиров id=% вышел на охоту', hunter_id;

    perform go_to_location_by_id(hunter_id, target_location_id);
    select id from type where name = 'вампир' INTO vampire_type;
    select COUNT(*) from character_type_nearby(hunter_id, 1) into vampire_cnt;
    RAISE NOTICE 'Вампиров рядом: %', vampire_cnt;
    if vampire_cnt > 1 then
        perform kill(hunter_id, (select id from character_type_nearby(hunter_id, 1) LIMIT 1),
                     'Охотник проиграл вампиру и умер');
        RAISE NOTICE 'Охотник проиграл вампиру и умер';
    elseif vampire_cnt = 1 then
        select blood_percentage
        from character c
        where c.id = (select id from character_type_nearby(hunter_id, 1))
        into vamp_blood_percentage;
        if vamp_blood_percentage != 100 then
            perform kill((select id from character_type_nearby(hunter_id, 1) LIMIT 1), hunter_id,
                         'Вампир слаб и его убил охотник');
            RAISE NOTICE 'Вампир слаб и его убил охотник';
        else
            perform kill(hunter_id, (select id from character_type_nearby(hunter_id, 1) LIMIT 1),
                         'Вампир силён и убил охотника');
            RAISE NOTICE 'Вампир силён и убил охотника';
        end if;
    else
        RAISE NOTICE 'Охотник не нашёл вампиров';
    end if;

end ;
$$;

alter function s311693.hunter_go_to_for_fight(integer, integer) owner to s311693;

