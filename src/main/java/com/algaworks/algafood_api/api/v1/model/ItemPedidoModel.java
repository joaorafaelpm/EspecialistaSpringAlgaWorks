package com.algaworks.algafood_api.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;


@Relation(collectionRelation = "itensPedido")
@Getter
@Setter
@AllArgsConstructor
public class ItemPedidoModel extends RepresentationModel<ItemPedidoModel> {

    private Long produtoId ;
    private String produtoNome ;
    private Integer quantidade ;
    private BigDecimal precoUnitario ;
    private BigDecimal precoTotal ;
    private String observacao;

}
