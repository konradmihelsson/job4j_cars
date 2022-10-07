CREATE TABLE auto_user
(
    id       SERIAL PRIMARY KEY,
    login    TEXT,
    password TEXT
);
CREATE TABLE auto_post
(
    id           SERIAL PRIMARY KEY,
    text         TEXT,
    created      TIMESTAMP default localtimestamp,
    auto_user_id INT REFERENCES auto_user (id)
);
