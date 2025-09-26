create table IF NOT EXISTS produto (
	id bigint not null auto_increment,
	restaurante_id bigint not null,
	nome varchar(200) not null,
	descricao text not null,
	preco decimal(10,2) not null,
	ativo tinyint(1) not null,

	primary key (id)
) engine=InnoDB default charset=utf8;

ALTER TABLE produto
ADD CONSTRAINT fk_produto_restaurante FOREIGN KEY (restaurante_id) REFERENCES restaurante(id);
