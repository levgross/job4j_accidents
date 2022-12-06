CREATE TABLE IF NOT EXISTS accident_rule (
   accident_id int REFERENCES accident(id),
   rule_id int REFERENCES rule(id),
   PRIMARY KEY(accident_id, rule_id)
);