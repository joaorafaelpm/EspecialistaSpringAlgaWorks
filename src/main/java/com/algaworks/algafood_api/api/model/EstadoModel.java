package com.algaworks.algafood_api.api.model;


import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "estados")
@Getter
@Setter
@AllArgsConstructor
public class EstadoModel extends RepresentationModel<EstadoModel> {

    private Long id ;
    private String nome ;

}

