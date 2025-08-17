-- Adiciona coluna password (permite NULL temporariamente)
ALTER TABLE users
    ADD COLUMN password VARCHAR(255);

-- Define senha temporária para registros existentes
UPDATE users
SET password = 'temp12345678'
WHERE password IS NULL;

-- MySQL usa MODIFY COLUMN, não ALTER COLUMN
ALTER TABLE users
    MODIFY COLUMN password VARCHAR(255) NOT NULL;

-- Adiciona constraint de validação
ALTER TABLE users
    ADD CONSTRAINT check_password_length
        CHECK (LENGTH(TRIM(password)) >= 8);