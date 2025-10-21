package com.algaworks.algafood_api.api.assembler.mapper;

import com.algaworks.algafood_api.api.model.CidadeModel;
import com.algaworks.algafood_api.domain.model.Cidade;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Bean;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CidadeMapper {

    @Bean
    CidadeModel toModel(Cidade cidade);


}
