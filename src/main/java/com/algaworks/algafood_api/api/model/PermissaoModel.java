package com.algaworks.algafood_api.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PermissaoModel {

    private Long id;
    private String nome;
    private String descricao;

}
