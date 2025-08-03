CREATE TABLE USER_TABLE (
    USER_ID varchar(256) PRIMARY KEY,
    EMAIL varchar(256) UNIQUE NOT NULL,
    CREATED_AT timestamp without time zone,
);

