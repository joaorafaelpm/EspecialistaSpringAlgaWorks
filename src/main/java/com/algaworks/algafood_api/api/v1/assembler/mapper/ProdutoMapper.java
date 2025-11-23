package com.algaworks.algafood_api.api.v1.assembler.mapper;

import com.algaworks.algafood_api.api.v1.model.ProdutoModel;
import com.algaworks.algafood_api.domain.model.Produto;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    @Bean
    ProdutoModel toModel(Produto produto);

    @Bean
    List<ProdutoModel> toCollection(Collection<Produto> listaProduto);

}
