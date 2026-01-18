package com.algaworks.algafood_api.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Relation(collectionRelation = "pedidos")
@Getter
@Setter
@AllArgsConstructor
public class PedidoModel extends RepresentationModel<PedidoModel> {

    @Schema(example = "936dc9ec-05bf-44e5-8c07-7e51adc6083d")
    private String codigo;

    @Schema(example = "298.90")
    private BigDecimal subtotal;

    @Schema(example = "10.00")
    private BigDecimal taxaFrete;

    @Schema(example = "308.90")
    private BigDecimal valorTotal;

    @Schema(example = "2022-12-01T20:34:04Z")
    private OffsetDateTime dataCriacao;

    @Schema(example = "2022-12-01T20:35:10Z")
    private OffsetDateTime dataConfirmacao;

    @Schema(example = "2022-12-01T20:55:30Z")
    private OffsetDateTime dataEntrega;

    @Schema(example = "2022-12-01T20:35:00Z")
    private OffsetDateTime dataCancelamento;

    @Schema(example = "CRIADO")
    private String status ;

    private RestauranteApenasNomeModel restaurante;

    private UsuarioModel cliente ;

    private EnderecoModel enderecoEntrega ;

    private FormaPagamentoModel formaPagamento ;

    private List<ItemPedidoModel> itens;

}
