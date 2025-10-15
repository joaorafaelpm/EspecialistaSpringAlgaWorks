package com.algaworks.algafood_api.api.controller;


import com.algaworks.algafood_api.api.assembler.FotoProdutoAssembler;
import com.algaworks.algafood_api.api.assembler.disassambler.FotoProdutoDisassembler;
import com.algaworks.algafood_api.api.model.FotoProdutoModel;
import com.algaworks.algafood_api.api.model.input.FotoProdutoDTO;
import com.algaworks.algafood_api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.model.FotoProduto;
import com.algaworks.algafood_api.domain.model.Produto;
import com.algaworks.algafood_api.domain.service.CadastroProdutoService;
import com.algaworks.algafood_api.domain.service.CatalogoFotoProdutoService;
import com.algaworks.algafood_api.domain.service.FotoStorageService;
import com.algaworks.algafood_api.infrastructure.storage.StorageException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
@AllArgsConstructor
public class RestauranteProdutoFotoController {

    private final CatalogoFotoProdutoService fotoProdutoService ;
    private final CadastroProdutoService produtoService ;
    private final FotoStorageService fotoStorageService;

    private final FotoProdutoAssembler fotoProdutoAssembler;
    private final FotoProdutoDisassembler fotoProdutoDisassembler;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public FotoProdutoModel pegarFoto (@PathVariable Long restauranteId , @PathVariable Long produtoId) {
        return fotoProdutoAssembler.fotoProdutoToFotoProdutoModel(
                fotoProdutoService.findById(restauranteId , produtoId));
    }

    @GetMapping
    public ResponseEntity<InputStreamResource> servirFoto (@PathVariable Long restauranteId , @PathVariable Long produtoId ,
                    @RequestHeader(name="accept") String acceptHeaders) throws HttpMediaTypeNotAcceptableException {
        try {
            FotoProduto fotoProduto = fotoProdutoService.findById(restauranteId, produtoId);

            MediaType mediaType = MediaType.parseMediaType(fotoProduto.getContentType());
            List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeaders);

            verificarCompatibilidadeMediaType(mediaType,mediaTypesAceitas);

            InputStream inputStream = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .body(new InputStreamResource(inputStream));
        }
        catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }

    }

//    A princípio, eu vou deixar isso no controller, já que temos um problema de infraestrutura, quando lançamos essa exceção nós temos que assegurar que não retorne nenhum json, o que não fazemos nesse momento dado que o ExceptionHandler trata a exceção e relança  passando um json, o que gera um not acceptable, mas não por que a mediatype ta errada, mas sim por que tratamos da forma errada
    private void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto, List<MediaType> mediaTypesAceitas) throws HttpMediaTypeNotAcceptableException {
        boolean compativel = mediaTypesAceitas.stream().anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));

        if (!compativel) {
            throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
        }
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoModel adicionarFoto (@PathVariable Long restauranteId , @PathVariable Long produtoId ,
                                           @Valid FotoProdutoDTO fotoProdutoDTO) throws IOException {

        Produto produto = produtoService.findById(restauranteId, produtoId);
        MultipartFile arquivo = fotoProdutoDTO.getArquivo();

        FotoProduto fotoProduto = fotoProdutoDisassembler.fotoProdutoDTOToFotoProduto(fotoProdutoDTO);
        fotoProduto.setProduto(produto);

        FotoProduto fotoSalva = fotoProdutoService.save(fotoProduto , arquivo.getInputStream());

        return fotoProdutoAssembler.fotoProdutoToFotoProdutoModel(fotoSalva);

    }
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerFoto (@PathVariable Long restauranteId , @PathVariable Long produtoId){
        fotoProdutoService.delete(restauranteId , produtoId);
    }

}
