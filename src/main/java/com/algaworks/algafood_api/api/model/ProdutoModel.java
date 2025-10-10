package com.algaworks.algafood_api.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class ProdutoModel {

    private Long id ;
    private String nome ;
    private String descricao ;
    private BigDecimal preco ;
    private Boolean ativo = false;

}
