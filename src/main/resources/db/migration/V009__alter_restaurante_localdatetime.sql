ALTER TABLE restaurante
ADD COLUMN data_cadastro     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN data_atualizacao  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP;

UPDATE restaurante
SET data_cadastro = CURRENT_TIMESTAMP,
    data_atualizacao = CURRENT_TIMESTAMP;

-- 3. Remove os valores default se quiser que futuras inserções preencham manualmente
--ALTER TABLE restaurante
--ALTER COLUMN data_cadastro DROP DEFAULT,
--ALTER COLUMN data_atualizacao DROP DEFAULT;
