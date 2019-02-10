# --- !Ups

ALTER TABLE POST
  ALTER COLUMN create_date SET DEFAULT CURRENT_TIMESTAMP;

-- Create sample dataset - Users
-- hint - pwd plaintext: "vancouver"
INSERT INTO USER (id, password, email, username, first_name, last_name, active)
VALUES
  (1, '$2a$04$bJ6BGR7qOGl5QMhHTZDNMOu3.Kx243qTss2XUZln4hlofcayst/PG', 'hai@hotmail.com', 'rickyhai11', 'ricky', 'hai',
   1);
-- hint - pwd plaintext: "vancouver"
INSERT INTO USER (id, password, email, username, first_name, last_name, active)
VALUES
  (2, '$2a$04$bJ6BGR7qOGl5QMhHTZDNMOu3.Kx243qTss2XUZln4hlofcayst/PG', 'tom@gmail.com', 'tom', 'Tom', 'Jerry', 1);
-- hint - pwd plaintext: "vancouver"
INSERT INTO USER (id, password, email, username, first_name, last_name, active)
VALUES (3, '$2a$04$bJ6BGR7qOGl5QMhHTZDNMOu3.Kx243qTss2XUZln4hlofcayst/PG', 'ha@gmail.com', 'ha', 'ha', 'jess', 1);

-- Create sample dataset - Roles
INSERT INTO ROLE (id, role)
VALUES (1, 'ROLE_ADMIN');
INSERT INTO ROLE (id, role)
VALUES (2, 'ROLE_USER');

-- User Roles
INSERT INTO USER_ROLE (user_id, role_id)
VALUES (1, 1);
INSERT INTO USER_ROLE (user_id, role_id)
VALUES (1, 2);
INSERT INTO USER_ROLE (user_id, role_id)
VALUES (2, 2);
INSERT INTO USER_ROLE (user_id, role_id)
VALUES (3, 2);

-- Create sample dataset - Posts
INSERT INTO POST (id, user_id, title, body, create_date)
VALUES (1, 1, 'Gris',
        '"Gris is about the fear we live with, and finding voice to defeat it"',
        CURRENT_TIMESTAMP());
INSERT INTO POST (id, user_id, title, body, create_date)
VALUES (2, 1, 'New Super Mario',
        '"New Super Mario Bros. U Deluxe is the first fantastic game of 2019."',
        CURRENT_TIMESTAMP());
INSERT INTO POST (id, user_id, title, body, create_date)
VALUES (3, 1, 'Tetris',
        '"Tetris Effect is euphoric, and the best Tetris game since the original."',
        CURRENT_TIMESTAMP());
INSERT INTO POST (id, user_id, title, body, create_date)
VALUES (4, 1, 'Hitman 2',
        '"Hitman 2 gives one of 2016’s best games a second shot at success."',
        CURRENT_TIMESTAMP());

-- Create sample dataset - Comments
INSERT INTO COMMENT (post_id, user_id, body, create_date)
VALUES (1, 1,
        '"Tetris Effect is euphoric, and the best Tetris game since the original."',
        CURRENT_TIMESTAMP());
INSERT INTO COMMENT (post_id, user_id, body, create_date)
VALUES (1, 2,
        '"Red Dead Redemption 2 review."',
        CURRENT_TIMESTAMP());
INSERT INTO COMMENT (post_id, user_id, body, create_date)
VALUES (1, 3,
        '"Bury Me, My Love is the stirring story of a refugee’s dangerous journey."',
        CURRENT_TIMESTAMP());
INSERT INTO COMMENT (post_id, user_id, body, create_date)
VALUES (2, 1,
        '"I have added a reminder to my calendar to buy this game. It looks great."',
        CURRENT_TIMESTAMP());
INSERT INTO COMMENT (post_id, user_id, body, create_date)
VALUES (3, 2,
        '"Wow. The animation and artwork is beautiful. Although it’s not the same aesthetically, it gives me the same feeling of when I first saw Journey."',
        CURRENT_TIMESTAMP());
INSERT INTO COMMENT (post_id, user_id, body, create_date)
VALUES (4, 3,
        '"Gonna have to add this to my Wish List."',
        CURRENT_TIMESTAMP());

# --- !Downs

delete from comment;
delete from post;
delete from user_role;
delete from user;
delete from role;
