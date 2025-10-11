package com.algaworks.algafood_api.api.model;

import com.algaworks.algafood_api.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CozinhaModel {
    @JsonView(value = RestauranteView.RestauranteResumo.class)
    private Long id ;
    private String nome ;
}
