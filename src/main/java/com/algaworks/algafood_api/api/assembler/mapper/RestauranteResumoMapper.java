package com.algaworks.algafood_api.api.assembler.mapper;

import com.algaworks.algafood_api.api.model.RestauranteApenasNomeModel;
import com.algaworks.algafood_api.api.model.RestauranteResumoModel;
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