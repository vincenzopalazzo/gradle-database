
    alter table Azienda 
        drop constraint FK7ojlederilbk6xuv92qeqo2t0;

    alter table Risposta 
        drop constraint FK5bbgd1w38mfos38kneyp76pv;

    alter table Risposta 
        drop constraint FK8deoukk2li6w355iomgtji8kv;

    alter table Sede 
        drop constraint FKp4lyqdlwqovonljbyk5c7av5l;

    drop table if exists Azienda cascade;

    drop table if exists hibernate_sequences cascade;

    drop table if exists Poll cascade;

    drop table if exists Risposta cascade;

    drop table if exists Sede cascade;

    drop sequence if exists hibernate_sequence;
create sequence hibernate_sequence start 1 increment 1;

    create table Azienda (
        id int8 not null,
        partitaIva varchar(255),
        regioneSociale varchar(255),
        sedeLegale_id int8,
        primary key (id)
    );

    create table hibernate_sequences (
        sequence_name varchar(255) not null,
        next_val int8,
        primary key (sequence_name)
    );

    create table Poll (
        id int8 not null,
        codice varchar(255),
        descrizione varchar(255),
        scadenza timestamp,
        tematica varchar(255),
        primary key (id)
    );

    create table Risposta (
        id int8 not null,
        dataAvvenimento timestamp,
        azienda_id int8,
        poll_id int8,
        primary key (id)
    );

    create table Sede (
        id int8 not null,
        citta varchar(255),
        provincia varchar(255),
        via varchar(255),
        azienda_id int8,
        primary key (id)
    );

    alter table Azienda 
        add constraint FK7ojlederilbk6xuv92qeqo2t0 
        foreign key (sedeLegale_id) 
        references Sede;

    alter table Risposta 
        add constraint FK5bbgd1w38mfos38kneyp76pv 
        foreign key (azienda_id) 
        references Azienda;

    alter table Risposta 
        add constraint FK8deoukk2li6w355iomgtji8kv 
        foreign key (poll_id) 
        references Poll;

    alter table Sede 
        add constraint FKp4lyqdlwqovonljbyk5c7av5l 
        foreign key (azienda_id) 
        references Azienda;
