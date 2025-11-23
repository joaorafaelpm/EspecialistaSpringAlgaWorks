package com.algaworks.algafood_api.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(collectionRelation = "produtos")
@Getter
@Setter
@AllArgsConstructor
public class ProdutoModel extends RepresentationModel<ProdutoModel> {

    private Long id ;
    private String nome ;
    private String descricao ;
    private BigDecimal preco ;
    private Boolean ativo;

}
