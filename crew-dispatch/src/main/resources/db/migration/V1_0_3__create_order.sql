CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) DEFAULT 'default password', -- varchar: 저장공간을 동적으로 할당, 길이가 짧으면 짧은 공간만 할당
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE,
    balance NUMERIC(10, 2) CHECK ( balance >= 0 ),
    age INT CHECK (age >= 18),
    country_code CHAR(2), -- char: 정적 길이만큼 공간할당, 남은길이는 공백, 빠른 검색
    address TEXT,
    profile_picture BYTEA,
    last_login TIMESTAMP,
    email_verified BOOLEAN DEFAULT FALSE,
    CONSTRAINT age_check CHECK (age >= 18)
    -- FOREIGN KEY (country_code) REFERENCES countries(code) ON DELETE SET NULL -- countries(code)가 제거되면 null
)