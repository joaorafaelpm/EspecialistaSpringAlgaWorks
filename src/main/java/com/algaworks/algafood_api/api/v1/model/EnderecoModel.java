package com.algaworks.algafood_api.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EnderecoModel {

    private String cep ;
    private String logradouro ;
    private String numero ;
    private String complemento ;
    private String bairro ;
    private CidadeResumoModel cidade ;

}
