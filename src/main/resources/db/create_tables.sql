DROP TABLE IF EXISTS favorite;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS locations;
DROP TABLE IF EXISTS users;

DROP SEQUENCE IF EXISTS user_id_seq;
DROP SEQUENCE IF EXISTS location_id_seq;
DROP SEQUENCE IF EXISTS post_id_seq;
DROP SEQUENCE IF EXISTS comment_id_seq;

CREATE SEQUENCE user_id_seq START 100000;
CREATE SEQUENCE location_id_seq START 100000;
CREATE SEQUENCE post_id_seq START 100000;
CREATE SEQUENCE comment_id_seq START 100000;

CREATE TABLE users
(
  user_id       INTEGER PRIMARY KEY DEFAULT nextval('user_id_seq'),
  name          VARCHAR,
  email         VARCHAR NOT NULL,
  password      VARCHAR NOT NULL,
  token         VARCHAR NOT NULL,
  advertiser    BOOLEAN NOT NULL DEFAULT FALSE,
  create_time   TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE locations
(
  location_id   INTEGER PRIMARY KEY DEFAULT nextval('location_id_seq'),
  location_name VARCHAR,
  latitude      NUMERIC NOT NULL,
  longitude     NUMERIC NOT NULL
);

CREATE TABLE posts
(
  post_id       INTEGER PRIMARY KEY DEFAULT nextval('post_id_seq'),
  location_id   INTEGER REFERENCES locations (location_id),
  user_id       INTEGER REFERENCES users (user_id),
  text          VARCHAR,
  category      VARCHAR DEFAULT 'ALL',
  create_time   TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE comments
(
  comment_id    INTEGER PRIMARY KEY DEFAULT nextval('comment_id_seq'),
  post_id       INTEGER REFERENCES posts (post_id),
  user_id       INTEGER REFERENCES users (user_id),
  text          VARCHAR,
  create_time   TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE favorite
(
  user_id       INTEGER REFERENCES users(user_id),
  post_id       INTEGER REFERENCES posts(post_id),
  PRIMARY KEY (user_id, post_id)
);