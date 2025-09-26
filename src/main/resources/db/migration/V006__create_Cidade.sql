CREATE TABLE IF NOT EXISTS cidade (
    id BIGINT AUTO_INCREMENT PRIMARY KEY ,
    nome VARCHAR(200) NOT NULL,
    estado_id BIGINT NOT NULL
) engine=InnoDB default charset=utf8;

ALTER TABLE cidade ADD CONSTRAINT fk_cidade FOREIGN KEY (estado_id) REFERENCES estado (id) ;
