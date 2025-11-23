package com.algaworks.algafood_api.api.v1.assembler.mapper;

import com.algaworks.algafood_api.api.v1.model.RestauranteApenasNomeModel;
import com.algaworks.algafood_api.api.v1.model.RestauranteResumoModel;
import com.algaworks.algafood_api.domain.model.Restaurante;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Bean;

@Mapper(componentModel = "spring")
public interface RestauranteResumoMapper {

    @Bean
    RestauranteResumoModel toModel(Restaurante restaurante);

    @Bean
    RestauranteApenasNomeModel toModelResumido (Restaurante restaurante);

}