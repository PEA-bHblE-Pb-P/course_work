create table s311693.vampire_to_servant
(
    servant_id integer not null
        references s311693.character,
    vampire_id integer not null
        references s311693.character,
    constraint unique_vampire_servant
        unique (vampire_id, servant_id)
);

alter table s311693.vampire_to_servant
    owner to s311693;

