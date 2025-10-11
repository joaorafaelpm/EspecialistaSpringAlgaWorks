create table IF NOT EXISTS pedido (
	id bigint not null auto_increment,
    subtotal DECIMAL(9 ,2) NOT NULL ,
    taxa_frete DECIMAL(9 ,2) NOT NULL ,
    valor_total DECIMAL(9 ,2) NOT NULL ,

    data_criacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_confirmacao DATETIME ,
    data_cancelamento DATETIME ,
    data_entrega DATETIME ,

    status_pedido VARCHAR(10) NOT NULL DEFAULT "CRIADO" ,
    forma_pagamento_id BIGINT NOT NULL ,
    restaurante_id BIGINT NOT NULL ,
    cliente_usuario_id BIGINT NOT NULL ,

    endereco_cidade_id BIGINT NOT NULL,
    endereco_logradouro VARCHAR(255) NOT NULL,
    endereco_numero VARCHAR(255) NOT NULL,
    endereco_complemento VARCHAR(255),
    endereco_bairro VARCHAR(255) NOT NULL,

    constraint fk_pedido_endereco_cidade foreign key (endereco_cidade_id) references cidade (id),
    constraint fk_pedido_restaurante foreign key (restaurante_id) references restaurante (id),
    constraint fk_pedido_usuario_cliente foreign key (cliente_usuario_id) references usuario (id),
    constraint fk_pedido_forma_pagamento foreign key (forma_pagamento_id) references forma_pagamento (id),

	primary key (id)
) engine=InnoDB default charset=utf8;
