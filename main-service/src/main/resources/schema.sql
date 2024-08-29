CREATE TABLE IF NOT EXISTS users (
    id BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    email varchar(40),
    name varchar(20),
    CONSTRAINT pk_user PRIMARY KEY (id)
);