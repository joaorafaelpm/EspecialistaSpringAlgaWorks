package com.algaworks.algafood_api.api.model;

import com.algaworks.algafood_api.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class RestauranteModel {

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


