package com.algaworks.algafood_api.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "restaurantes")
@Getter
@Setter
@AllArgsConstructor
public class RestauranteApenasNomeModel extends RepresentationModel<RestauranteApenasNomeModel> {

    private Long id;
    private String nome;

}
