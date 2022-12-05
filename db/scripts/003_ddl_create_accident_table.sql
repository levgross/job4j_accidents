CREATE TABLE IF NOT EXISTS accident (
  id serial primary key,
  name varchar,
  text varchar,
  address varchar,
  type_id int not null references type(id)
);