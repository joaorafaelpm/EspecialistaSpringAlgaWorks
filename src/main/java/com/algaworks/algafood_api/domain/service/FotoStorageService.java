package com.algaworks.algafood_api.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

public interface FotoStorageService {

    void armazenar (NovaFoto novaFoto);

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
