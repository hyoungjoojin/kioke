CREATE TABLE PROFILE_TABLE (
    USER_ID varchar(256) PRIMARY KEY REFERENCES USER_TABLE (USER_ID),
    NAME varchar(256),
    IS_ONBOARDED boolean NOT NULL,
    CREATED_AT timestamp without time zone,
    LAST_MODIFIED_AT timestamp without time zone
);

