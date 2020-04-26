CREATE TABLE price (
    id SERIAL PRIMARY KEY,
    one_event INTEGER NOT NULL,
    two_events INTEGER NOT NULL
);

INSERT INTO price (one_event, two_events) VALUES (30, 40);