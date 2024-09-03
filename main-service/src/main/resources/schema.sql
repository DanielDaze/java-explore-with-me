CREATE TABLE IF NOT EXISTS users (
    id BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    email VARCHAR UNIQUE,
    name VARCHAR,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS categories (
    id BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    name VARCHAR UNIQUE,
    CONSTRAINT pk_category PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS events (
    id BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    title VARCHAR,
    annotation VARCHAR,
    category_id BIGINT REFERENCES categories (id),
    description VARCHAR,
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
    state VARCHAR,
    views INTEGER,
    CONSTRAINT pk_event PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS requests (
    id BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    created TIMESTAMP WITHOUT TIME ZONE,
    event_id BIGINT REFERENCES events (id) ON DELETE CASCADE,
    requester_id BIGINT REFERENCES users (id) ON DELETE CASCADE,
    status varchar,
    CONSTRAINT pk_request PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS compilations (
    id BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    pinned BOOLEAN,
    title varchar,
    CONSTRAINT pk_compilation PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS compilations_events (
    events_id BIGINT NOT NULL REFERENCES events (id) ON DELETE SET NULL,
    compilation_id BIGINT REFERENCES compilations (id) ON DELETE CASCADE
);