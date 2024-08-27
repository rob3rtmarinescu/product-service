create table products
(
    id                    int not null auto_increment,
    name                  varchar(500) not null,
    description           varchar(2000),
    price                 number(10, 5)
);

create unique index product_id_idx on products (id asc);

alter table products add constraint products_pk primary key (id)
    using index product_id_idx enable;