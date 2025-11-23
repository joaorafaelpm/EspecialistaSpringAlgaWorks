package com.algaworks.algafood_api.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "usuarios")
@Getter
@Setter
@AllArgsConstructor
public class UsuarioModel extends RepresentationModel<UsuarioModel> {

    private Long id ;
    private String nome ;
    private String email;

}
