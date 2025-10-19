package com.algaworks.algafood_api.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "formas-pagamento")
@Getter
@Setter
@AllArgsConstructor
public class FormaPagamentoModel extends RepresentationModel {

    private Long id ;
    private String descricao;
}
