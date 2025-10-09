package com.algaworks.algafood_api.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UsuarioModel {

    private Long id ;
    private String nome ;
    private String email;

}
