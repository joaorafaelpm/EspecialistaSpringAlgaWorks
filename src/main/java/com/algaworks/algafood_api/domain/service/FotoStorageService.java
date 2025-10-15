package com.algaworks.algafood_api.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

public interface FotoStorageService {

    InputStream recuperar (String nomeArquivo);

    void armazenar (NovaFoto novaFoto);

    void remover (String nomeArquivo);

    default void substituir (String nomeArquivoExistente , NovaFoto novaFoto) {
//        As regras de negócio para substituir uma imagem por outra é, primeiro armazenar, e se já existir, remover a antiga
//        É por isso que nós só atribuimos o UUID quando nós vamos guardar a imagem, e não aqui na função de armazenar, serve para nós mantermos o controle das classes/arquivos
        this.armazenar(novaFoto);

        if (nomeArquivoExistente != null) {
            remover(nomeArquivoExistente);
        }
    }

    default String gerarNomeArquivo (String nomeOriginal ) {
        return UUID.randomUUID() + "_" + nomeOriginal;
    }

    @Getter
    @Builder
    class NovaFoto {
        private String nomeArquivo;
        private InputStream inputStream;
    }

}
