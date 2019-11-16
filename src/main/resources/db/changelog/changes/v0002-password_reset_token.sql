CREATE TABLE password_reset_token (
  id SERIAL PRIMARY KEY,
  token VARCHAR(12) NOT NULL,
  user_id INTEGER REFERENCES users(id) NOT NULL,
  expiry_date TIMESTAMPTZ NOT NULL
);