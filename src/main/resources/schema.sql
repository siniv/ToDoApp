/*DROP TABLE IF EXISTS astia;

CREATE TABLE astia (
    id serial primary key,
    nimi VARCHAR(255) NOT NULL,
    lkm integer,
    toimipaikka_id integer
);

DROP TABLE IF EXISTS toimipaikka;

CREATE TABLE toimipaikka (
    id serial primary key,
    nimi VARCHAR(255) NOT NULL,
    sijainti VARCHAR(255),
    aloitus_vuosi integer
);

INSERT INTO toimipaikka(nimi, sijainti,aloitus_vuosi) VALUES('Academy Finland', 'Espoo', '2017'),('Academy Sweden', 'Kista', '2015'),('Academy Germany', 'Munchen', '2018'),('Wyncode Academy', 'Miami', '2010');

alter table astia
    add constraint astia_toimipaikka_id_fk
        foreign key (toimipaikka_id) references toimipaikka;*/

DROP TABLE IF EXISTS task;

CREATE TABLE task (
id serial primary key,
name VARCHAR(255) NOT NULL
);