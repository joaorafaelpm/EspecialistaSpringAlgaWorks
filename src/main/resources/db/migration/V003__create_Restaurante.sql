CREATE TABLE IF NOT EXISTS restaurante (
    id BIGINT AUTO_INCREMENT PRIMARY KEY ,
    nome VARCHAR(200) NOT NULL ,
    taxa_frete DECIMAL(9,2) NOT NULL ,
    cozinha_id BIGINT NOT NULL
    );

ALTER TABLE restaurante ADD FOREIGN KEY (cozinha_id) REFERENCES cozinha (id) ;


