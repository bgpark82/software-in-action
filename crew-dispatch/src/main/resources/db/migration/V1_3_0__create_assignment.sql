CREATE TABLE assignment (
    id BIGSERIAL PRIMARY KEY,
    crew_id BIGINT,
    route_id BIGINT,
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    status VARCHAR(255),
    CONSTRAINT fk_crew FOREIGN KEY (crew_id) REFERENCES crew(crew_id) ON DELETE CASCADE ,
    CONSTRAINT fk_route FOREIGN KEY (route_id) REFERENCES route(route_id) ON DELETE CASCADE
)