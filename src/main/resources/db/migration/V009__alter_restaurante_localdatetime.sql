ALTER TABLE restaurante
ADD COLUMN data_cadastro     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN data_atualizacao  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP;

UPDATE restaurante
SET data_cadastro = utc_timestamp,
    data_atualizacao = utc_timestamp;

-- 3. Remove os valores default se quiser que futuras inserções preencham manualmente
--ALTER TABLE restaurante
--ALTER COLUMN data_cadastro DROP DEFAULT,
--ALTER COLUMN data_atualizacao DROP DEFAULT;
