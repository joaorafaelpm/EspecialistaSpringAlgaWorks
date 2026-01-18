package com.algaworks.algafood_api.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(example = "1")
    private Long id ;
    @Schema(example = "Roberto")
    private String nome ;
    @Schema(example = "roberto@gmail.com")
    private String email;

}
