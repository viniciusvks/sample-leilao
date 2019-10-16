create table usuarios
(
    id   int auto_increment,
    nome varchar(255) not null,
    constraint usuarios_pk unique (id)
);
create table leiloes
(
    id          int auto_increment primary key,
    produto     varchar(255) null,
    data_inicio date         null,
    encerrado   tinyint(1)   null
);
create table lances
(
    id            int auto_increment primary key,
    proponente_id int              null,
    valor         double default 0 null,
    id_leilao     int              not null,
    constraint lances_leiloes_id_fk
        foreign key (id_leilao) references leiloes (id)
            on update cascade on delete cascade,
    constraint lances_usuarios_id_fk
        foreign key (proponente_id) references usuarios (id)
            on update cascade on delete cascade
);
