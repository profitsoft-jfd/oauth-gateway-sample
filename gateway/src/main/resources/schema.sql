CREATE TABLE IF NOT EXISTS user_session (
    id VARCHAR(50) PRIMARY KEY,
    email VARCHAR(50),
    name VARCHAR(100),
    expires_at DATETIME
);
