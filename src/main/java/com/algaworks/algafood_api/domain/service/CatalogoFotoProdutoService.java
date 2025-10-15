package com.algaworks.algafood_api.domain.service;

import com.algaworks.algafood_api.domain.exception.FotoProdutoNaoEncontradoException;
import com.algaworks.algafood_api.domain.model.FotoProduto;
import com.algaworks.algafood_api.domain.model.Produto;
import com.algaworks.algafood_api.domain.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CatalogoFotoProdutoService {

    private ProdutoRepository produtoRepository;

    private FotoStorageService fotoStorageService;

    @Transactional
    public FotoProduto findById (Long restauranteId , Long produtoId) {
        return produtoRepository.findFotoById(restauranteId , produtoId).orElseThrow(() ->
                new FotoProdutoNaoEncontradoException(restauranteId , produtoId));
    }


    @Transactional
    public FotoProduto save (FotoProduto foto , InputStream dadosArquivo) {
        Long restauranteId = foto.getRestauranteId();
        Long produtoId = foto.getProduto().getId();
        String nomeNovoArquivo = fotoStorageService.gerarNomeArquivo(foto.getNomeArquivo());

        Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(restauranteId, produtoId);

        String nomeArquivoExistente = null ;

        if (fotoExistente.isPresent()) {
            nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
            produtoRepository.delete(fotoExistente.get());
        }

//        A ideia é salvar primeiro para caso haja algum erro ele não salve a imagem no disco, já que o JPA vai fazer o roll back automaticamente


        foto.setNomeArquivo(nomeNovoArquivo);
        FotoProduto fotoSalva = produtoRepository.save(foto);
        produtoRepository.flush();

        FotoStorageService.NovaFoto novaFoto = FotoStorageService.NovaFoto.builder()
                .nomeArquivo(foto.getNomeArquivo())
                .inputStream(dadosArquivo)
                .build();

        fotoStorageService.substituir(nomeArquivoExistente , novaFoto);

        return fotoSalva;
    }

    @Transactional
    public void delete (Long restauranteId , Long produtoId) {
        FotoProduto fotoExistente = findById(restauranteId, produtoId);
        String nomeArquivoExistente = fotoExistente.getNomeArquivo();
        fotoStorageService.remover(nomeArquivoExistente);
        produtoRepository.delete(fotoExistente);
        produtoRepository.flush();
    }


}
