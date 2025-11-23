package com.algaworks.algafood_api.api.v2.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cozinhas")
@Getter
@Setter
@AllArgsConstructor
public class CozinhaModelV2 extends RepresentationModel<CozinhaModelV2> {
    private Long idCozinha ;
    private String nomeCozinha ;
}
