CREATE TABLE users
(
  userId        INTEGER PRIMARY KEY,
  name          VARCHAR,
  email         VARCHAR NOT NULL,
  password      VARCHAR NOT NULL,
  regTime       TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE posts
(
  postId        INTEGER PRIMARY KEY,
  locationId    INTEGER FOREIGN KEY,
  userId        INTEGER FOREIGN KEY,
  text          VARCHAR,
  createTime    TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE locations
(
  locationId    INTEGER PRIMARY KEY,
  locationName  VARCHAR,
  latitude      NUMERIC NOT NULL,
  longitude     NUMERIC NOT NULL
);

CREATE TABLE comments
(
  commentId     INTEGER PRIMARY KEY,
  postId        INTEGER PRIMARY KEY,
  userId        INTEGER PRIMARY KEY,
  text          VARCHAR,
  createTime    TIMESTAMP NOT NULL DEFAULT now()
);