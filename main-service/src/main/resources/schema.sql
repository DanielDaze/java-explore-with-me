CREATE TABLE IF NOT EXISTS users (
    id BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    email VARCHAR(40) UNIQUE,
    name VARCHAR(20),
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS categories (
    id BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    name VARCHAR(30) UNIQUE,
    CONSTRAINT pk_category PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS events (
    id BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    title VARCHAR(30),
    annotation VARCHAR(60),
    category_id BIGINT REFERENCES categories (id),
    description VARCHAR(1000),
    event_date TIMESTAMP WITHOUT TIME ZONE,
    lat FLOAT,
    lon FLOAT,
    paid BOOLEAN,
    participant_limit INTEGER,
    request_moderation BOOLEAN,
    confirmed_requests INTEGER,
    created_on TIMESTAMP WITHOUT TIME ZONE,
    initiator_id BIGINT REFERENCES users (id),
    published_on TIMESTAMP WITHOUT TIME ZONE,
    state VARCHAR(12),
    views INTEGER,
    CONSTRAINT pk_event PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS requests (
    id BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    created TIMESTAMP WITHOUT TIME ZONE,
    event_id BIGINT REFERENCES events (id) ON DELETE CASCADE,
    requester_id BIGINT REFERENCES users (id) ON DELETE CASCADE,
    status varchar(20),
    CONSTRAINT pk_request PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS compilations (
    id BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    pinned BOOLEAN,
    title varchar(30),
    CONSTRAINT pk_compilation PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS compilation_events (
    compilation_id BIGINT REFERENCES compilations (id) ON DELETE CASCADE,
    event_id BIGINT REFERENCES events (id) ON DELETE SET NULL
);