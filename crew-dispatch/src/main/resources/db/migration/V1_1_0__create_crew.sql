CREATE TABLE crew (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    mobile VARCHAR(255) UNIQUE,
    status VARCHAR(255),
    last_rest_time TIMESTAMP,
    monthly_work_hours INT
)