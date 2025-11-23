package com.algaworks.algafood_api.api.v2.assembler.mapper;

import com.algaworks.algafood_api.api.v2.model.CozinhaModelV2;
import com.algaworks.algafood_api.domain.model.Cozinha;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Bean;

@Mapper(componentModel = "spring")
public interface CozinhaMapperV2 {

    @Bean
    @Mapping(source = "id" , target = "idCozinha")
    @Mapping(source = "nome" , target = "nomeCozinha")
    CozinhaModelV2 toModel(Cozinha cozinha);

}
