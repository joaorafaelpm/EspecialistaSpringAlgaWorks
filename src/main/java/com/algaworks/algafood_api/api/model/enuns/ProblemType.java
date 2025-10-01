package com.algaworks.algafood_api.api.model.enuns;

import lombok.Getter;

@Getter
public enum ProblemType {

    MENSSAGEM_INCOMPREESSIVEL("/menssagem-incompreenssivel" , "Menssagem Incompreenssível."),
    ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada" , "Entidade não encontrada.") ,
    ENTIDADE_EM_USO("/entidade-esta-em-uso" , "Entidade está sendo usada.") ,
    NEGOCIO_EXCEPTION("/erro-de-negocio" , "Houve uma violação da regra de negócio.") ;

    private String path;
    private String tittle;

    ProblemType (String path , String tittle) {
        this.path = "https://algafood.com.br" + path ;
        this.tittle = tittle;
    }





}
