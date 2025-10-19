package com.algaworks.algafood_api.api.model;

import com.algaworks.algafood_api.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cozinhas")
@Getter
@Setter
@AllArgsConstructor
public class CozinhaModel extends RepresentationModel<CozinhaModel> {
    @JsonView(value = RestauranteView.RestauranteResumo.class)
    private Long id ;
    private String nome ;
}
