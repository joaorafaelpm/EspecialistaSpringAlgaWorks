package com.algaworks.algafood_api.api.v1.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "estados")
@Getter
@Setter
@AllArgsConstructor
public class EstadoModel extends RepresentationModel<EstadoModel> {

    @Schema(example = "1")
    private Long id ;
    @Schema(example = "São Paulo")
    private String nome ;

}

