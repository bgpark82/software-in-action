CREATE TABLE route (
    route_id BIGSERIAL PRIMARY KEY,
    route_number VARCHAR(255),
    start_location VARCHAR(255),
    end_location VARCHAR(255),
    estimated_minutes INT
)