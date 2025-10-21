package com.algaworks.algafood_api.api.assembler.mapper;

import com.algaworks.algafood_api.api.model.CozinhaModel;
import com.algaworks.algafood_api.domain.model.Cozinha;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CozinhaMapper {

    @Bean
    CozinhaModel toModel(Cozinha cozinha);

}
