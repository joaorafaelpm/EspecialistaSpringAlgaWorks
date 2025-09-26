create table IF NOT EXISTS produto (
	id bigint not null auto_increment,
    sub_total DECIMAL(9 ,2) NOT NULL ,
    taxa_frete DECIMAL(9 ,2) NOT NULL ,
    valor_total DECIMAL(9 ,2) NOT NULL ,
    data_criacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_confirmacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_cancelamento DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_entrega DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status_pedido VARCHAR(10) NOT NULL DEFAULT "CRIADO" ,
    forma_pagamento_id BIGINT NOT NULL ,
    restaurante_id BIGINT NOT NULL ,
    cliente_id BIGINT NOT NULL ,
    endereco_cidade_id BIGINT NOT NULL,
    endereco_logradouro VARCHAR(255) NOT NULL,
    endereco_numero VARCHAR(255) NOT NULL,
    endereco_complemento VARCHAR(255),
    endereco_bairro VARCHAR(255) NOT NULL,

	primary key (id)
) engine=InnoDB default charset=utf8;

ALTER TABLE produto ADD CONSTRAINT fk_endereco_cidade
    FOREIGN KEY (endereco_cidade_id)
    REFERENCES cidade(id);

ALTER TABLE produto
    ADD CONSTRAINT fk_forma_pagamento_id
        FOREIGN KEY (forma_pagamento_id)
        REFERENCES forma_pagamento(id);
ALTER TABLE produto
    ADD CONSTRAINT fk_cliente
            FOREIGN KEY (cliente_id)
            REFERENCES usuarios(id);

ALTER TABLE produto
    ADD CONSTRAINT fk_restaurante
            FOREIGN KEY (restaurante_id)
            REFERENCES restaurante(id);
