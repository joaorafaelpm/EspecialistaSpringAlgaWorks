package com.algaworks.algafood_api.api.v1.assembler.mapper;

import com.algaworks.algafood_api.api.v1.model.FotoProdutoModel;
import com.algaworks.algafood_api.domain.model.FotoProduto;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Bean;

@Mapper(componentModel = "spring")
public interface FotoProdutoMapper {

    @Bean
    FotoProdutoModel toModel(FotoProduto fotoProduto);

}

