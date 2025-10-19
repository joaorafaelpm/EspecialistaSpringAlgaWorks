package com.algaworks.algafood_api.api.model;

import com.algaworks.algafood_api.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;
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

    @JsonView({RestauranteView.RestauranteResumo .class , RestauranteView.ApenasNome.class})
    private Long id;
    @JsonView({RestauranteView.RestauranteResumo .class , RestauranteView.ApenasNome.class})
    private String nome;
    @JsonView(value = RestauranteView.RestauranteResumo.class)
    private BigDecimal taxaFrete;
    @JsonView(value = RestauranteView.RestauranteResumo.class)
    private CozinhaModel cozinha;

    private Boolean ativo ;
    private Boolean aberto ;
    private EnderecoModel endereco ;

}


