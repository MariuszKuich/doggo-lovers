CREATE TABLE USERS (
    USER_ID serial PRIMARY KEY,
    USERNAME VARCHAR(50) UNIQUE NOT NULL,
    PASSWORD VARCHAR(200) NOT NULL
);