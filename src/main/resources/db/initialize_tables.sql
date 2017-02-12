INSERT INTO users (name, email, password)
VALUES ('TestName', 'user@test.ru', 'password_in_md5');
INSERT INTO users (name, email, password)
VALUES ('User', 'user@gmail.com', 'pass');

INSERT INTO locations (locationname, latitude, longitude)
VALUES ('Street', 2.28, 3.22);
INSERT INTO locations (locationname, latitude, longitude)
VALUES ('Ylica', 14.88, 50.51);

INSERT INTO posts (locationId, userId, text)
VALUES (100000, 100000, 'Message1');
INSERT INTO posts (locationId, userId, text)
VALUES (100001, 100001, 'Big message and big text and big text  faf  fsdf sdf d afsdf sdf sdfsdfatf iuayf hiashf');

INSERT INTO comments (postid, userid, text)
VALUES (100000, 100000, 'Comment1');
INSERT INTO comments (postid, userid, text)
VALUES (100001, 100001, 'Samara National Research University is one of the leading universities recognized not only in Russia but abroad. It trains specialists in space-rocket engineering, aviation, radio electronics, metallurgy, automotive industry, information technologies, business studies, social sciences, languages, law and other fields.');