CREATE TABLE IF NOT EXISTS users
(
    id              UUID PRIMARY KEY,
    email           VARCHAR(255),
    first_name      VARCHAR(255),
    second_name     VARCHAR(255),
    version         BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS locations
(
    id              UUID PRIMARY KEY,
    user_id         UUID NOT NULL,
    created_at      TIMESTAMP WITH TIME ZONE NOT NULL,
    latitude        NUMERIC,
    longitude       NUMERIC
);
CREATE INDEX IF NOT EXISTS user_created_at_location_idx ON locations (user_id, created_at);
