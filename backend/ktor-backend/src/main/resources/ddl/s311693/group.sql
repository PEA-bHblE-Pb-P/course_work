create table s311693."group"
(
    id          serial
        primary key,
    location_id integer
        references s311693.location,
    admin_id    integer
        references s311693.character,
    name        varchar
);

alter table s311693."group"
    owner to s311693;

