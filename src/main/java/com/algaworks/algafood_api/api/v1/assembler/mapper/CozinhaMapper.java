package com.algaworks.algafood_api.api.v1.assembler.mapper;

import com.algaworks.algafood_api.api.v1.model.CozinhaModel;
import com.algaworks.algafood_api.domain.model.Cozinha;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Bean;

@Mapper(componentModel = "spring")
public interface CozinhaMapper {

    @Bean
    CozinhaModel toModel(Cozinha cozinha);

}
