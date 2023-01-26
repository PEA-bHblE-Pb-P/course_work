create table s311693.location
(
    id               serial
        primary key,
    lat              double precision,
    lon              double precision,
    name             varchar,
    location_type_id integer
        references s311693.location_type,
    photo_url        text
);

alter table s311693.location
    owner to s311693;

