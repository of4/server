INSERT INTO users (name, email, password, token)
VALUES ('TestName', 'testName@test.ru', 'password_in_md5', 'hjuxehc48pm6at4tsy83mwl5eo85np6y4a0jhhbkgh12h1uk2f');
INSERT INTO users (name, email, password, advertiser, token)
VALUES ('User', 'user@gmail.com', 'pass', TRUE, 'jyy99lt5v30ftbtobfyrb85vsxw5mw1frts5dwo4umg36khfr0');

INSERT INTO locations (location_name, latitude, longitude)
VALUES ('Street 1', 2.28, 3.22);
INSERT INTO locations (location_name, latitude, longitude)
VALUES ('Street 2', 14.88, 50.51);

INSERT INTO posts (location_id, user_id, text, category)
VALUES (100000, 100000, 'Message1', 'EDU');
INSERT INTO posts (location_id, user_id, text, category)
VALUES (100001, 100001, 'Text text text text text text text text text text', 'SHO');

INSERT INTO comments (post_id, user_id, text)
VALUES (100000, 100000, 'Comment 1');
INSERT INTO comments (post_id, user_id, text)
VALUES (100001, 100001, ' Comment comment comment comment');

INSERT INTO favorite (user_id, post_id)
VALUES (100000, 100001);