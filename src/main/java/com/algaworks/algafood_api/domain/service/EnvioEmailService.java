package com.algaworks.algafood_api.domain.service;


import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Set;

public interface EnvioEmailService {

    @Autowired
    void enviar(Menssagem menssagem) ;

    @Getter
    @Builder
    class Menssagem {

//        Esse @Singular significa que ao invés de passar um novo objeto set ele simplifica para "destinatario" ou seja, ele cria uma string única, como no set, e deixa esse objeto adicionável como uma lista simplesmente passando outro destinatário
        @Singular
        private Set<String> destinatarios ;

        @NonNull
        private String assunto ;
        @NonNull
        private String corpo ;

//        O singular não sabe transformar variaveis de plural para singular (kkkkk burro pcrlh)
        @Singular("variavel")
        private Map<String , Object> variaveis ;

    }

}
