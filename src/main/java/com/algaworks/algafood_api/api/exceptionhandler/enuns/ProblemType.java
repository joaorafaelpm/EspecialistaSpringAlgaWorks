package com.algaworks.algafood_api.api.exceptionhandler.enuns;

import lombok.Getter;

@Getter
public enum ProblemType {

    MENSAGEM_INCOMPREESSIVEL("/mensagem-incompreenssivel" , "Mensagem Incompreenssível."),
    DADOS_INVALIDOS("/dados-invalidos" , "Algum dado foi inserido de forma incorreta."),
    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado" , "Recurso não encontrado."),
    ERRO_DE_SISTEMA("/erro-inesperado" , "Erro inesperado."),
    PARAMETRO_INVALIDO("/parametro-invalido" , "Parâmetro inválido na URL"),
    ENTIDADE_EM_USO("/entidade-esta-em-uso" , "Entidade está sendo usada.") ,
    NEGOCIO_EXCEPTION("/erro-de-negocio" , "Houve uma violação da regra de negócio.") ,
    AUTHORITY_EXCEPTION("/acesso-negado" , "Acesso negado.") ;

    private String path;
    private String title;

    ProblemType (String path , String title) {
        this.path = "https://algafood.com.br" + path ;
        this.title = title;
    }





}
