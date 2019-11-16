CREATE TABLE users(
  id SERIAL PRIMARY KEY,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  created TIMESTAMPTZ NOT NULL,
  updated TIMESTAMPTZ NOT NULL,
  enabled boolean NOT NULL,
  admin boolean NOT NULL
);

CREATE UNIQUE INDEX lower_users_email_idx ON users ((lower(email)));