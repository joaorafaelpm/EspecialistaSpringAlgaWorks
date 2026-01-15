package com.algaworks.algafood_api.api.v1.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;


@Relation(collectionRelation = "cidades")
@Getter
@Setter
@AllArgsConstructor
public class CidadeModel extends RepresentationModel<CidadeModel> {

    @Schema(example = "1")
    private Long id ;

    @Schema(example = "Campinas")
    private String nome ;

    private EstadoModel estado ;

}

