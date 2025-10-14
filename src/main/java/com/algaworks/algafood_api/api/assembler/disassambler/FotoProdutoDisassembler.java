package com.algaworks.algafood_api.api.assembler.disassambler;

import com.algaworks.algafood_api.api.model.input.FotoProdutoDTO;
import com.algaworks.algafood_api.domain.model.FotoProduto;
import org.mapstruct.*;
import org.springframework.context.annotation.Bean;

@Mapper(componentModel = "spring")
public interface FotoProdutoDisassembler {

//    Recebo o arquivo e a descricao do ProdutoDTO
    @Bean
    @Mapping(target = "id" , ignore = true)
    @Mapping(target = "produto" , ignore = true)
    @Mapping(source = "arquivo.contentType" , target = "contentType")
    @Mapping(source = "arquivo.size" , target = "tamanho")
    @Mapping(source = "arquivo.originalFilename" , target = "nomeArquivo")
    FotoProduto fotoProdutoDTOToFotoProduto (FotoProdutoDTO fotoProdutoDTO) ;

}