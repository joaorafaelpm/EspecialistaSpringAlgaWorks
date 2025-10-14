package com.algaworks.algafood_api.infrastructure.storage;

import com.algaworks.algafood_api.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class LocalFotoStorageService implements FotoStorageService {


//    Coloquei o path no application.properties
    @Value(value = "${algafood.sotorage.local.direction.diretorio-fotos}")
    private Path diretorioFotos ;

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            Path arquivoPath = getArquivoPath(novaFoto.getNomeArquivo() );
            FileCopyUtils.copy(novaFoto.getInputStream() , Files.newOutputStream(arquivoPath));
        } catch (Exception e) {
            throw new StorageException("Erro ao armazenar arquivo." , e);
        }
    }

    private Path getArquivoPath(String nomeArquivo) {
        return diretorioFotos.resolve(Path.of(nomeArquivo));
    }
}
