package com.algaworks.algafood_api.api.v1.assembler.mapper;

import com.algaworks.algafood_api.api.v1.model.CidadeModel;
import com.algaworks.algafood_api.domain.model.Cidade;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Bean;

@Mapper(componentModel = "spring")
public interface CidadeMapper {

    @Bean
    CidadeModel toModel(Cidade cidade);


}
