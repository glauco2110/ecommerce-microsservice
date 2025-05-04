create table tb_product
(
    price       float(53)    not null,
    description varchar(2000),
    category_id varchar(255) not null,
    id          varchar(255) not null,
    name        varchar(255) not null unique,
    primary key (id)
);