create table s311693.character
(
    id                 serial
        primary key,
    type_id            integer
        references s311693.type,
    name               varchar,
    birthday           date
        constraint verifybirthday
            check (birthday < CURRENT_TIMESTAMP),
    history            text
        constraint unique_story
            unique,
    sex_id             integer
        references s311693.sex,
    place_of_living_id integer
        references s311693.location,
    location_id        integer
        references s311693.location,
    "group"            integer
        references s311693."group",
    favor_id           integer
        references s311693.favor,
    blood_percentage   integer default 100 not null,
    photo_url          text
);

alter table s311693.character
    owner to s311693;

create index characters_location_id
    on s311693.character using hash (location_id);

create trigger blood_amount_trigger
    after update
        of blood_percentage
    on s311693.character
    for each row
execute procedure s311693.do_something_if_blood_amount_is_nok();

create trigger kill_trigger
    after update
        of location_id
    on s311693.character
    for each row
execute procedure s311693.execution_cannot_be_pardoned();

