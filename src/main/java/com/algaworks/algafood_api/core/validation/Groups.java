package com.algaworks.algafood_api.core.validation;

// Definindo grupos para especificar melhor as validações, afinal para ocasiões diferentes nós temos que ter validações diferentes
public interface Groups {

//    Essa especificação limita a Classe de Cozinha para validar somente o Id quando eu for criar alguma coisa que depende somente do Id da cozinha
    public interface CozinhaId {}

    public interface EstadoId {}
}
