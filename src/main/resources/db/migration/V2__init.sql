-- initialize sequences and tables
drop sequence if exists ui.users_seq;
create sequence ui.users_seq;

drop table if exists ui.user_authority;
drop table if exists ui.users;
drop table if exists ui.authority;

create table ui.users
(
	id bigint not null
		constraint user_pkey
			primary key,
	activated boolean not null,
	email varchar(50) not null,
	firstname varchar(50) not null,
	lastname varchar(50) not null,
	password varchar(100) not null,
	username varchar(50) not null
		constraint uk_sb8bbouer5wak8vyiiy4pf2bx
			unique
);

create table ui.authority
(
	name varchar(50) not null
		constraint authority_pkey
			primary key
);

create table ui.user_authority
(
	user_id bigint not null
		constraint fkpqlsjpkybgos9w2svcri7j8xy
			references ui.users,
	authority_name varchar(50) not null
		constraint fk6ktglpl5mjosa283rvken2py5
			references ui.authority,
	constraint user_authority_pkey
		primary key (user_id, authority_name)
);


INSERT INTO ui.users (ID, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, EMAIL, ACTIVATED) VALUES (1, 'admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'admin', 'admin', 'admin@admin.com', true);
INSERT INTO ui.users (ID, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, EMAIL, ACTIVATED) VALUES (2, 'user', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'user', 'user', 'enabled@user.com', true);
INSERT INTO ui.users (ID, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, EMAIL, ACTIVATED) VALUES (3, 'disabled', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'user', 'user', 'disabled@user.com', false);

INSERT INTO ui.authority (NAME) VALUES ('ROLE_USER');
INSERT INTO ui.authority (NAME) VALUES ('ROLE_ADMIN');

INSERT INTO ui.user_authority (USER_ID, AUTHORITY_NAME) VALUES (1, 'ROLE_ADMIN');
INSERT INTO ui.user_authority (USER_ID, AUTHORITY_NAME) VALUES (1, 'ROLE_USER');
INSERT INTO ui.user_authority (USER_ID, AUTHORITY_NAME) VALUES (2, 'ROLE_USER');
INSERT INTO ui.user_authority (USER_ID, AUTHORITY_NAME) VALUES (3, 'ROLE_USER');

drop table if exists ui.item;
create table ui.item
(
    id bigint not null
        constraint item_pkey
            primary key,
    itemname varchar(50) not null,
    description varchar(1000)
);

INSERT INTO ui.item (ID, ITEMNAME, DESCRIPTION) VALUES (1, 'Item One Name', 'Item One Description');
INSERT INTO ui.item (ID, ITEMNAME, DESCRIPTION) VALUES (2, 'Item Two Name', 'Item Two Description');

CREATE OR REPLACE FUNCTION ui.f_get_item_by_parameter(i_filter_key jsonb)
    RETURNS jsonb
    LANGUAGE 'plpgsql'
    VOLATILE
    PARALLEL UNSAFE
    COST 100
AS $BODY$declare
    v_result			jsonb;
    v_resources			jsonb;
begin
    select coalesce(json_agg(row_to_json(t))::jsonb,'{}'::jsonb)
    into v_resources
    from (select ID, ITEMNAME, DESCRIPTION from ui.item) t;

    select row_to_json(t)::jsonb
    into v_result
    from (select v_resources as items) t;

    return v_result;
end;
$BODY$;
