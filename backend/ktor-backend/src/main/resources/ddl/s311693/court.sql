create table s311693.court
(
    id           serial
        primary key,
    verdict      varchar,
    date         date,
    defendant_id integer
        references s311693.character,
    group_id     integer
        references s311693."group",
    location_id  integer
        references s311693.location
);

alter table s311693.court
    owner to s311693;

