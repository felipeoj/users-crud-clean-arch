ALTER TABLE users
    ADD COLUMN password VARCHAR(255);

UPDATE users
SET password = 'temp12345678'
WHERE password IS NULL;

ALTER TABLE users
    MODIFY COLUMN password VARCHAR(255) NOT NULL;

ALTER TABLE users
    ADD CONSTRAINT check_password_length
        CHECK (LENGTH(TRIM(password)) >= 8);