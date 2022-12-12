create table s311693.location_history
(
    character_id integer                 not null
        references s311693.character,
    location_id  integer
        references s311693.location,
    visit_time   timestamp default now() not null,
    primary key (character_id, visit_time)
);

alter table s311693.location_history
    owner to s311693;

