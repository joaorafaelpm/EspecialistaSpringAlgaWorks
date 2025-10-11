package com.algaworks.algafood_api.api.assembler.disassambler;

import com.algaworks.algafood_api.api.model.input.ProdutoDTO;
import com.algaworks.algafood_api.domain.model.Produto;
import org.mapstruct.*;
import org.springframework.context.annotation.Bean;

@Mapper(componentModel = "spring")
public interface ProdutoDisassembler {

    @Bean

    Produto produtoDTOToProduto (ProdutoDTO produtoDTO) ;

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateProdutoFromDto(ProdutoDTO dto, @MappingTarget Produto entity);

}
