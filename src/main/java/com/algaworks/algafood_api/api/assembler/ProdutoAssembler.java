package com.algaworks.algafood_api.api.assembler;

import com.algaworks.algafood_api.api.model.ProdutoModel;
import com.algaworks.algafood_api.domain.model.Produto;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ProdutoAssembler {

    @Bean
    ProdutoModel produtoToProdutoModel(Produto produto);

    @Bean
    List<ProdutoModel> toCollection(Collection<Produto> listaProduto);

}
