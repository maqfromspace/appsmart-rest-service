/* Create customers table */
create table customers (
                           id uuid not null,
                           created_at timestamp,
                           is_deleted boolean,
                           modified_at timestamp,
                           title varchar(255),
                           primary key (id)
)

/* Create products table */
create table products (
                          id uuid not null,
                          created_at timestamp,
                          is_deleted boolean,
                          description varchar(255),
                          modified_at timestamp,
                          price float8,
                          title varchar(255),
                          customer_id uuid not null,
                          primary key (id)
)

/* Create roles table */
create table roles (
                       id int8 not null,
                       name varchar(255),
                       primary key (id)
)

/* Create users table */
create table users (
                       id int8 not null,
                       created_at timestamp,
                       modified_at timestamp,
                       password varchar(255),
                       username varchar(255),
                       primary key (id)
)

/* Create user_roles table */
create table user_roles (
                            user_id int8 not null,
                            role_id int8 not null
)