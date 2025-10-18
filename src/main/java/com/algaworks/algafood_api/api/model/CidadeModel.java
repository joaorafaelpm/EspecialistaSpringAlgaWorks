package com.algaworks.algafood_api.api.model;


import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;


@Relation(collectionRelation = "cidades")
@Getter
@Setter
@AllArgsConstructor
public class CidadeModel extends RepresentationModel<CidadeModel> {

    private Long id ;
    private String nome ;
    private EstadoModel estado ;

}

