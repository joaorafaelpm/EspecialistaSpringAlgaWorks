set foreign_key_checks = 0;

lock tables cidade write, cozinha write, estado write, forma_pagamento write,
	grupo write, grupo_permissao write, permissao write,
	produto write, restaurante write, restaurante_forma_pagamento write,
	restaurante_usuario_responsavel write, usuario write, usuario_grupo write,
	pedido write, item_pedido write, foto_produto write, oauth2_registered_client write;

delete from cidade;
delete from cozinha;
delete from estado;
delete from forma_pagamento;
delete from grupo;
delete from grupo_permissao;
delete from permissao;
delete from produto;
delete from restaurante;
delete from restaurante_forma_pagamento;
delete from usuario;
delete from usuario_grupo;
delete from restaurante_usuario_responsavel;
delete from pedido;
delete from item_pedido;
delete from foto_produto;
delete from oauth2_registered_client;

set foreign_key_checks = 1;

alter table cidade auto_increment = 1;
alter table cozinha auto_increment = 1;
alter table estado auto_increment = 1;
alter table forma_pagamento auto_increment = 1;
alter table grupo auto_increment = 1;
alter table permissao auto_increment = 1;
alter table produto auto_increment = 1;
alter table restaurante auto_increment = 1;
alter table usuario auto_increment = 1;
alter table pedido auto_increment = 1;
alter table item_pedido auto_increment = 1;


insert into cozinha (id, nome) values (1, 'Tailandesa');
insert into cozinha (id, nome) values (2, 'Indiana');
insert into cozinha (id, nome) values (3, 'Argentina');
insert into cozinha (id, nome) values (4, 'Brasileira');

insert into estado (id, nome) values (1, 'Minas Gerais');
insert into estado (id, nome) values (2, 'São Paulo');
insert into estado (id, nome) values (3, 'Ceará');

insert into cidade (id, nome, estado_id) values (1, 'Uberlândia', 1);
insert into cidade (id, nome, estado_id) values (2, 'Belo Horizonte', 1);
insert into cidade (id, nome, estado_id) values (3, 'São Paulo', 2);
insert into cidade (id, nome, estado_id) values (4, 'Campinas', 2);
insert into cidade (id, nome, estado_id) values (5, 'Fortaleza', 3);

insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro) values (1, 'Thai Gourmet', 10, 1, utc_timestamp, utc_timestamp, true, true, 1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro');
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) values (2, 'Thai Delivery', 9.50, 1, utc_timestamp, utc_timestamp, true, true);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) values (3, 'Tuk Tuk Comida Indiana', 15, 2, utc_timestamp, utc_timestamp, true, true);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) values (4, 'Java Steakhouse', 12, 3, utc_timestamp, utc_timestamp, true, true);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) values (5, 'Lanchonete do Tio Sam', 11, 4, utc_timestamp, utc_timestamp, true, true);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) values (6, 'Bar da Maria', 6, 4, utc_timestamp, utc_timestamp, true, true);

insert into forma_pagamento (id, descricao , data_atualizacao) values (1, 'Cartão de crédito' , utc_timestamp);
insert into forma_pagamento (id, descricao , data_atualizacao) values (2, 'Cartão de débito' , utc_timestamp);
insert into forma_pagamento (id, descricao , data_atualizacao) values (3, 'Dinheiro' , utc_timestamp);


insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (5, 1), (5, 2), (6, 3);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 1, 1);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Camarão tailandês', '16 camarões grandes ao molho picante', 110, 0, 1);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 2);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 1, 3);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 3);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 4);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 1, 4);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 5);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 6);

insert into grupo (nome) values ('Gerente'), ('Vendedor'), ('Secretária'), ('Cadastrador');

insert into permissao (id, nome, descricao) values (1, 'EDITAR_COZINHAS', 'Permite editar cozinhas');
insert into permissao (id, nome, descricao) values (2, 'EDITAR_FORMAS_PAGAMENTO', 'Permite criar ou editar formas de pagamento');
insert into permissao (id, nome, descricao) values (3, 'EDITAR_CIDADES', 'Permite criar ou editar cidades');
insert into permissao (id, nome, descricao) values (4, 'EDITAR_ESTADOS', 'Permite criar ou editar estados');
insert into permissao (id, nome, descricao) values (5, 'CONSULTAR_USUARIOS_GRUPOS_PERMISSOES', 'Permite consultar usuários');
insert into permissao (id, nome, descricao) values (6, 'EDITAR_USUARIOS_GRUPOS_PERMISSOES', 'Permite criar ou editar usuários');
insert into permissao (id, nome, descricao) values (7, 'EDITAR_RESTAURANTES', 'Permite criar, editar ou gerenciar restaurantes');
insert into permissao (id, nome, descricao) values (8, 'CONSULTAR_PEDIDOS', 'Permite consultar pedidos');
insert into permissao (id, nome, descricao) values (9, 'GERENCIAR_PEDIDOS', 'Permite gerenciar pedidos');
insert into permissao (id, nome, descricao) values (10, 'GERAR_RELATORIOS', 'Permite gerar relatórios');


-- Adiciona todas as permissoes no grupo do gerente
insert into grupo_permissao (grupo_id, permissao_id)
-- Gerente pode fazer tudo
select 1, id from permissao;

-- Adiciona permissoes no grupo do vendedor
insert into grupo_permissao (grupo_id, permissao_id)
#Vendedor só tem acesso às consultas da API
select 2, id from permissao where nome like 'CONSULTAR_%';

insert into grupo_permissao (grupo_id, permissao_id) values (2, 9);

-- Adiciona permissoes no grupo do auxiliar
insert into grupo_permissao (grupo_id, permissao_id)
-- Auxiliar só tem acesso às consultas da API
select 3, id from permissao where nome like 'CONSULTAR_%';

-- Adiciona permissoes no grupo cadastrador
insert into grupo_permissao (grupo_id, permissao_id)
-- Cadastrador faz o que quiser com as tabelas de restaurantes e produtos
select 4, id from permissao where nome like '%_RESTAURANTES';

insert into usuario (id, nome, email, senha, data_cadastro) values
(1, 'João da Silva', 'joao.ger@algafood.com', '$2a$12$xM3T9jhJ/qTbQ8yKkFyapOJeD.xzlaOB.CIgaKUBBsSfxw2dAbzM6', utc_timestamp),
(2, 'Maria Joaquina', 'maria.vnd@algafood.com', '$2a$12$xM3T9jhJ/qTbQ8yKkFyapOJeD.xzlaOB.CIgaKUBBsSfxw2dAbzM6', utc_timestamp),
(3, 'Roberto fazbear', 'guinas.sec@algafood.com', '$2a$12$xM3T9jhJ/qTbQ8yKkFyapOJeD.xzlaOB.CIgaKUBBsSfxw2dAbzM6', utc_timestamp),
(4, 'La ele da silva', 'alele.cad@algafood.com', '$2a$12$xM3T9jhJ/qTbQ8yKkFyapOJeD.xzlaOB.CIgaKUBBsSfxw2dAbzM6', utc_timestamp),
(5, 'José Souza', 'email.teste.pendezzapizza.tcc+hubert@gmail.com', '$2a$12$xM3T9jhJ/qTbQ8yKkFyapOJeD.xzlaOB.CIgaKUBBsSfxw2dAbzM6', utc_timestamp),
(6, 'Sebastião Martins', 'email.teste.pendezzapizza.tcc+sebastiao@gmail.com', '$2a$12$xM3T9jhJ/qTbQ8yKkFyapOJeD.xzlaOB.CIgaKUBBsSfxw2dAbzM6', utc_timestamp),
(7, 'Ronaldo Pinto', 'cocoxixicocopinto@gmail.com', '$2a$12$xM3T9jhJ/qTbQ8yKkFyapOJeD.xzlaOB.CIgaKUBBsSfxw2dAbzM6', utc_timestamp);

insert into usuario_grupo (usuario_id, grupo_id) values (1, 1), (2, 2), (3, 3) , (4,4);

insert into restaurante_usuario_responsavel (usuario_id , restaurante_id) values (5 , 1) , (5 , 3) , (6 , 2) , (6 , 4);

insert into pedido (id,codigo, restaurante_id, cliente_usuario_id, forma_pagamento_id, endereco_cidade_id, endereco_cep,
    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
    status_pedido, data_criacao, subtotal, taxa_frete, valor_total)
values (1,"936dc9ec-05bf-44e5-8c07-7e51adc6083d", 1, 1, 1, 1, '38400-000', 'Rua Floriano Peixoto', '500', 'Apto 801', 'Brasil',
'CRIADO', utc_timestamp, 298.90, 10, 308.90);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (1, 1, 1, 1, 78.9, 78.9, null);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (2, 1, 2, 2, 110, 220, 'Menos picante, por favor');


insert into pedido (id,codigo, restaurante_id, cliente_usuario_id, forma_pagamento_id, endereco_cidade_id, endereco_cep,
        endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
        status_pedido, data_criacao, subtotal, taxa_frete, valor_total)
values (2,"56dda8a4-d53d-489e-a859-f246882a94bf",4, 1, 2, 1, '38400-111', 'Rua Acre', '300', 'Casa 2', 'Centro',
'CRIADO', utc_timestamp, 79, 0, 79);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (3, 2, 6, 1, 79, 79, 'Ao ponto');

insert into pedido (id,codigo, restaurante_id, cliente_usuario_id, forma_pagamento_id, endereco_cidade_id, endereco_cep,
            endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
            status_pedido, data_criacao, data_confirmacao , data_entrega , subtotal, taxa_frete, valor_total)
values (3, 'b5741512-8fbc-47fa-9ac1-b530354fc0ff', 1, 1, 1, 1, '38400-222', 'Rua Natal', '200', null, 'Brasil',
        'ENTREGUE', '2025-10-11 21:10:00', '2025-10-11 21:10:45', '2025-10-11 21:55:44', 110, 10, 120);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (4, 3, 2, 1, 110, 110, null);


insert into pedido (id,codigo, restaurante_id, cliente_usuario_id, forma_pagamento_id, endereco_cidade_id, endereco_cep,
            endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
            status_pedido, data_criacao, data_confirmacao , data_entrega , subtotal, taxa_frete, valor_total)
values (4, '5c621c9a-ba61-4454-8631-8aabefe58dc2', 1, 2, 1, 1, '38400-800', 'Rua Fortaleza', '900', 'Apto 504', 'Centro',
        'ENTREGUE', '2025-10-02 20:34:04', '2025-10-02 20:35:10', '2025-10-02 21:10:32', 174.4, 5, 179.4);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (5, 4, 3, 2, 87.2, 174.4, null);


insert into pedido (id,codigo, restaurante_id, cliente_usuario_id, forma_pagamento_id, endereco_cidade_id, endereco_cep,
            endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
            status_pedido, data_criacao, data_confirmacao , data_entrega ,subtotal, taxa_frete, valor_total)
values (5, '8d774bcf-b238-42f3-aef1-5fb388754d63', 1, 3, 2, 1, '38400-200', 'Rua 10', '930', 'Casa 20', 'Martins',
        'ENTREGUE', '2025-10-03 02:00:30', '2025-10-03 02:01:21', '2025-10-03 02:20:10', 87.2, 10, 97.2);

insert into pedido (id,codigo, restaurante_id, cliente_usuario_id, forma_pagamento_id, endereco_cidade_id, endereco_cep,
            endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
            status_pedido, data_criacao,subtotal, taxa_frete, valor_total)

values (6, 'a26b8850-453e-4ffb-bc71-4165f26c288c', 1, 3, 2, 1, '38400-200', 'Rua 10', '930', 'Casa 20', 'Martins',
        'CRIADO', '2025-10-03 02:00:30', 110, 10, 120.0);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (7, 6, 2, 1, 110, 110, null);


insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (6, 5, 3, 1, 87.2, 87.2, null);


-- Como a tabela mudou da última stack do spring security até agora , eu estou usando o que o spring inseriu na minha tabela e simplesmente copiei e passei para cá, se necessário alteração, ou um modo de configuração mais simplificado, basta adicionar pelo código no AuthorizationServerConfig e depois salvar com a classe de jdbc, que ele vai criar a row automaticamente no banco de dados e basta copia-la e fazer um insert no afterMingrate com aqueles dados
-- Neste commit eu vou deixar o código disponível para documentar no caso de eu necessitar isto no futuro e para isso vou salvar o commit como "insert auth client doc"
insert into oauth2_registered_client
   (id, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_name, client_authentication_methods, authorization_grant_types, redirect_uris, scopes, client_settings, token_settings, post_logout_redirect_uris)
values ('1', 'algafood-end', '2026-01-01 18:40:31', '$2a$10$jDmmfbXgnLCCK4xHFMzlXu2zOEI9GQkvIy7YUuRqpPUrKoQjHptfO', NULL, 'AlgaFood Backend', 'client_secret_basic', 'client_credentials', '', 'READ,WRITE', '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.client.require-proof-key\":false,\"settings.client.require-authorization-consent\":true}', '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.token.reuse-refresh-tokens\":true,\"settings.token.x509-certificate-bound-access-tokens\":false,\"settings.token.id-token-signature-algorithm\":[\"org.springframework.security.oauth2.jose.jws.SignatureAlgorithm\",\"RS256\"],\"settings.token.access-token-time-to-live\":[\"java.time.Duration\",1800.000000000],\"settings.token.access-token-format\":{\"@class\":\"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat\",\"value\":\"self-contained\"},\"settings.token.refresh-token-time-to-live\":[\"java.time.Duration\",3600.000000000],\"settings.token.authorization-code-time-to-live\":[\"java.time.Duration\",300.000000000],\"settings.token.device-code-time-to-live\":[\"java.time.Duration\",300.000000000]}', '') ,
 ('2', 'algafood-analytics', '2026-01-01 20:32:11', '$2a$10$TTSW6kuqePj/KTioft9HLufrFUpuL2Zk7v4A1DXbY/5s3Fw5fv1uK', NULL, 'AlgaFood Web', 'client_secret_basic', 'client_credentials', '', 'READ,WRITE', '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.client.require-proof-key\":false,\"settings.client.require-authorization-consent\":false}', '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.token.reuse-refresh-tokens\":true,\"settings.token.x509-certificate-bound-access-tokens\":false,\"settings.token.id-token-signature-algorithm\":[\"org.springframework.security.oauth2.jose.jws.SignatureAlgorithm\",\"RS256\"],\"settings.token.access-token-time-to-live\":[\"java.time.Duration\",1800.000000000],\"settings.token.access-token-format\":{\"@class\":\"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat\",\"value\":\"self-contained\"},\"settings.token.refresh-token-time-to-live\":[\"java.time.Duration\",3600.000000000],\"settings.token.authorization-code-time-to-live\":[\"java.time.Duration\",300.000000000],\"settings.token.device-code-time-to-live\":[\"java.time.Duration\",300.000000000]}', ''),
 ('3', 'algafood-web', '2026-01-01 18:40:31', '$2a$10$utkQ6W9.Pd0/1aSMkieno.jadbC1Hwq8ajcxWNVI2NO20jKx2.sA6', NULL, 'AlgaFood Analytics', 'client_secret_basic', 'refresh_token,authorization_code', 'http://localhost:8081/laele', 'READ,WRITE', '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.client.require-proof-key\":true,\"settings.client.require-authorization-consent\":true}', '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.token.reuse-refresh-tokens\":false,\"settings.token.x509-certificate-bound-access-tokens\":false,\"settings.token.id-token-signature-algorithm\":[\"org.springframework.security.oauth2.jose.jws.SignatureAlgorithm\",\"RS256\"],\"settings.token.access-token-time-to-live\":[\"java.time.Duration\",1800.000000000],\"settings.token.access-token-format\":{\"@class\":\"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat\",\"value\":\"self-contained\"},\"settings.token.refresh-token-time-to-live\":[\"java.time.Duration\",86400.000000000],\"settings.token.authorization-code-time-to-live\":[\"java.time.Duration\",300.000000000],\"settings.token.device-code-time-to-live\":[\"java.time.Duration\",300.000000000]}', '');

unlock tables;