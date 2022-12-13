create table s311693.murder
(
    id          serial
        primary key,
    killer_id   integer
        references s311693.character,
    victim      integer
        references s311693.character,
    description varchar,
    date        timestamp
);

alter table s311693.murder
    owner to s311693;

