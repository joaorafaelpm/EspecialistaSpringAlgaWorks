package com.algaworks.algafood_api.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(collectionRelation = "restaurantes")
@Getter
@Setter
@AllArgsConstructor
public class RestauranteModel extends RepresentationModel<RestauranteModel> {

    @Schema(example = "1")
    private Long id;
    @Schema(example = "Thai Gourmet")
    private String nome;
    @Schema(example = "10.00")
    private BigDecimal taxaFrete;
    private CozinhaModel cozinha;

    private Boolean ativo ;
    private Boolean aberto ;
    private EnderecoModel endereco ;

}


