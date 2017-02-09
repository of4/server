CREATE TABLE users
(
  id         INTEGER PRIMARY KEY,
  name       VARCHAR,
  email      VARCHAR NOT NULL,
  password   VARCHAR NOT NULL,
  registered TIMESTAMP NOT NULL DEFAULT now()
);