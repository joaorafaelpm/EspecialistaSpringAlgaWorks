package com.algaworks.algafood_api.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
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


    @Schema(example = "1")
    private Long produtoId ;
    @Schema(example = "Porco com molho agridoce")
    private String produtoNome ;
    @Schema(example = "1")
    private Integer quantidade ;
    @Schema(example = "78.90")
    private BigDecimal precoUnitario ;
    @Schema(example = "78.90")
    private BigDecimal precoTotal ;
    @Schema(example = "Sem pimenta, por  favor")
    private String observacao;

}
