package com.algafood.javaclient.client.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class RestauranteResumoModel {

    private Long id ;
    private String nome ;
    private BigDecimal taxaFrete ;
    private CozinhaModel cozinha;
    private EnderecoModel endereco ;

}
