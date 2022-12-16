insert into authorities (authority) values ('ROLE_USER');
insert into authorities (authority) values ('ROLE_ADMIN');

insert into users (username, enabled, password, authority_id)
values ('root', true, '$2a$10$nawI67EK4xgHZgC.nf6.KOW2uX63MVlZHqh82j3n1Fngtfw4pSQqW',
(select id from authorities where authority = 'ROLE_ADMIN'));