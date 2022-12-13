create table s311693.passes
(
    character_id integer not null
        references s311693.character,
    location_id  integer not null
        references s311693.location,
    primary key (character_id, location_id)
);

alter table s311693.passes
    owner to s311693;

