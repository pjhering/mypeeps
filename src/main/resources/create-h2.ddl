
create table if not exists person
(
    id          bigint not null generated always as identity,
    givenname   varchar(200) not null,
    familyname  varchar(200) not null,
    gender      varchar(20) not null,
    notes       varchar(2000)
);

create table if not exists parent_child
(
    parent_id   bigint not null,
    child_id    bigint not null,
    primary key(parent_id, child_id),
    foreign key (parent_id) references person (id) on delete cascade,
    foreign key (child_id) references person (id) on delete cascade,
    check (parent_id != child_id)
);

create table if not exists event
(
    id          bigint not null generated always as identity,
    person_id   bigint not null,
    title       varchar(200) not null,
    occurred    date not null,
    place       varchar(200) not null,
    notes       varchar(2000),
    foreign key (person_id) references person (id) on delete cascade
);

create table if not exists file
(
    id          bigint not null generated always as identity,
    path        varchar(400) not null,
    description varchar(400)
);

create table if not exists person_file
(
    person_id   bigint not null,
    file_id     bigint not null,
    primary key (person_id, file_id),
    foreign key (person_id) references person (id) on delete cascade,
    foreign key (file_id) references file (id) on delete cascade
);

create table if not exists event_file
(
    event_id    bigint not null,
    file_id     bigint not null,
    primary key (event_id, file_id),
    foreign key (event_id) references event (id) on delete cascade,
    foreign key (file_id) references file (id) on delete cascade
);
