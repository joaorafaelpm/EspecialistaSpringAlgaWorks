CREATE TABLE IF NOT EXISTS forma_pagamento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(100) NOT NULL
) engine=InnoDB default charset=utf8;