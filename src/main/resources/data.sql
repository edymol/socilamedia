INSERT INTO user_details(id, birth_date, name)
VALUES (10001, CURRENT_DATE, 'Edy');
INSERT INTO user_details(id, birth_date, name)
VALUES (10002, CURRENT_DATE, 'Tania');
INSERT INTO user_details(id, birth_date, name)
VALUES (10003, CURRENT_DATE, 'Edith');

INSERT INTO post(id, description, user_id)
VALUES (2012, 'Determined to learn', 10001);
-- Inserting a post for user with ID 10001
INSERT INTO post(id, description, user_id)
VALUES (2013, 'Exploring new technologies', 10001);

-- Inserting a post for user with ID 10002
INSERT INTO post(id, description, user_id)
VALUES (2014, 'Enjoying the coding journey', 10002);

-- Inserting a post for user with ID 10003
INSERT INTO post(id, description, user_id)
VALUES (2015, 'Building cool projects', 10003);