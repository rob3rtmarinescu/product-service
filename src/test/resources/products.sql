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

insert into products (name, description, price) values ('Credit card', 'Opening a credit card', 100.0);
insert into products (name, description, price) values ('Debit card', 'Opening a debit card', 0.0);
insert into products (name, description, price) values ('Loan', 'Bank loan', 1000.0);