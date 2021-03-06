
create table IF NOT EXISTS articles
(
    id    varchar(10) not null
        primary key,
    name  varchar(100) not null,
    stock int          not null
);

create table IF NOT EXISTS products
(
    name  varchar(100) not null
        primary key,
    stock int          not null
);

create table IF NOT EXISTS product_articles
(
    article_id   varchar(10) not null,
    amount       int          not null,
    product_name varchar(100) not null,
    primary key (article_id, product_name),
    constraint product_articles_product_FK
        foreign key (product_name) references products (name),
    constraint product_articles_article_FK
        foreign key (article_id) references articles (id)
);

create table IF NOT EXISTS sells
(
    id         bigint auto_increment
        primary key,
    amount     int          not null,
    product_id varchar(100) not null,
    constraint sells_product_FK
        foreign key (product_id) references products (name)
);

