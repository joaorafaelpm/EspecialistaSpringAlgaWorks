CREATE TABLE IF NOT EXISTS restaurante_usuario_responsavel (
    usuario_id BIGINT NOT NULL,
    restaurante_id BIGINT NOT NULL,

    PRIMARY KEY (usuario_id , restaurante_id),

    CONSTRAINT fk_restaurante_usuario_usuario FOREIGN KEY (usuario_id)
            REFERENCES usuario(id),
    CONSTRAINT fk_restaurante_usuario_restaurante FOREIGN KEY (restaurante_id)
            REFERENCES restaurante(id)
) engine=InnoDB default charset=utf8;
