package com.algaworks.algafood_api.api.v1.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cidade")
@Getter
@Setter
@AllArgsConstructor
public class CidadeResumoModel extends RepresentationModel<CidadeResumoModel> {

    @Schema(example = "1")
    private Long id ;
    @Schema(example = "Campinas")
    private String nome ;
    @Schema(example = "São Paulo")
    private String estado ;

}

