package com.algaworks.algafood_api.api.assembler;

import com.algaworks.algafood_api.api.model.CozinhaModel;
import com.algaworks.algafood_api.domain.model.Cozinha;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Bean;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CozinhaAssembler {

    @Bean
    CozinhaModel cozinhaToCozinhaModel(Cozinha cozinha);

    @Bean
    List<CozinhaModel> toCollection(List<Cozinha> listaCozinha);

}
