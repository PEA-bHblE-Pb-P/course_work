create table s311693.favor
(
    id      serial
        primary key,
    sex_id  integer
        references s311693.sex,
    age_min integer,
    age_max integer,
    constraint unique_favor
        unique (age_max, age_min, sex_id)
);

alter table s311693.favor
    owner to s311693;

create trigger age_trigger
    before insert or update
    on s311693.favor
    for each row
execute procedure s311693.validateage();

