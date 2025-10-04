package com.algaworks.algafood_api.api.model.enuns;

import lombok.Getter;

@Getter
public enum ProblemType {

    MENSSAGEM_INCOMPREESSIVEL("/menssagem-incompreenssivel" , "Menssagem Incompreenssível."),
    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado" , "Recurso não encontrado."),
    ERRO_DE_SISTEMA("/erro-inesperado" , "Erro inesperado."),
    PARAMETRO_INVALIDO("/parametro-invalido" , "Parâmetro inválido na URL"),
    ENTIDADE_EM_USO("/entidade-esta-em-uso" , "Entidade está sendo usada.") ,
    NEGOCIO_EXCEPTION("/erro-de-negocio" , "Houve uma violação da regra de negócio.") ;

    private String path;
    private String tittle;

    ProblemType (String path , String tittle) {
        this.path = "https://algafood.com.br" + path ;
        this.tittle = tittle;
    }





}
