CREATE TABLE registration (
  id SERIAL PRIMARY KEY,
  user_id INTEGER REFERENCES users(id) NOT NULL,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  birth_month INTEGER NOT NULL,
  birth_year INTEGER NOT NULL,
  baseball BOOLEAN NOT NULL,
  soccer BOOLEAN NOT NULL,
  cost INTEGER NOT NULL,
  created TIMESTAMPTZ NOT NULL
);