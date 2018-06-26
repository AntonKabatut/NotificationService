CREATE TABLE users (
  id       SERIAL PRIMARY KEY,
  login    VARCHAR(33) NOT NULL,
  password VARCHAR(33) NOT NULL
);

CREATE TABLE subjects (
  id      SERIAL PRIMARY KEY,
  name    VARCHAR(33) NOT NULL,
  user_id BIGINT      NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE labs (
  id            SERIAL PRIMARY KEY,
  name          VARCHAR(33) NOT NULL,
  deadline      BIGINT      NOT NULL,
  day_of_notify BIGINT      NOT NULL,
  need_notify   BOOLEAN     NOT NULL,
  is_notified   BOOLEAN     NOT NULL DEFAULT FALSE,
  subject_id    BIGINT      NOT NULL,
  FOREIGN KEY (subject_id) REFERENCES subjects (id)
);