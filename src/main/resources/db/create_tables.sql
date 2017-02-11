DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS locations;
DROP TABLE IF EXISTS comments;

DROP SEQUENCE IF EXISTS user_id_seq;
DROP SEQUENCE IF EXISTS post_id_seq;
DROP SEQUENCE IF EXISTS comment_id_seq;
DROP SEQUENCE IF EXISTS location_id_seq;

CREATE SEQUENCE user_id_seq START 100000;
CREATE SEQUENCE post_id_seq START 100000;
CREATE SEQUENCE comment_id_seq START 100000;
CREATE SEQUENCE location_id_seq START 100000;

CREATE TABLE users
(
  userId        INTEGER PRIMARY KEY DEFAULT nextval('user_id_seq'),
  name          VARCHAR,
  email         VARCHAR NOT NULL,
  password      VARCHAR NOT NULL,
  regTime       TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE locations
(
  locationId    INTEGER PRIMARY KEY DEFAULT nextval('location_id_seq'),
  locationName  VARCHAR,
  latitude      NUMERIC NOT NULL,
  longitude     NUMERIC NOT NULL
);

CREATE TABLE posts
(
  postId        INTEGER PRIMARY KEY DEFAULT nextval('post_id_seq'),
  locationId    INTEGER REFERENCES locations (locationId),
  userId        INTEGER REFERENCES users (userId),
  text          VARCHAR,
  createTime    TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE comments
(
  commentId     INTEGER PRIMARY KEY DEFAULT nextval('comment_id_seq'),
  postId        INTEGER REFERENCES posts (postId),
  userId        INTEGER REFERENCES users (userId),
  text          VARCHAR,
  createTime    TIMESTAMP NOT NULL DEFAULT now()
);