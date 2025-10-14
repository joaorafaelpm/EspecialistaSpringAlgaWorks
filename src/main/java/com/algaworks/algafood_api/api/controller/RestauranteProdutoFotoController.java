package com.algaworks.algafood_api.api.controller;


import com.algaworks.algafood_api.api.assembler.FotoProdutoAssembler;
import com.algaworks.algafood_api.api.assembler.disassambler.FotoProdutoDisassembler;
import com.algaworks.algafood_api.api.model.FotoProdutoModel;
import com.algaworks.algafood_api.api.model.input.FotoProdutoDTO;
import com.algaworks.algafood_api.domain.model.FotoProduto;
import com.algaworks.algafood_api.domain.model.Produto;
import com.algaworks.algafood_api.domain.service.CadastroProdutoService;
import com.algaworks.algafood_api.domain.service.CatalogoFotoProdutoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
@AllArgsConstructor
public class RestauranteProdutoFotoController {

    private final CatalogoFotoProdutoService fotoProdutoService ;
    private final CadastroProdutoService produtoService ;

    private final FotoProdutoAssembler fotoProdutoAssembler;
    private final FotoProdutoDisassembler fotoProdutoDisassembler;

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

}
