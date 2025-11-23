package com.algaworks.algafood_api.api.v1.assembler.mapper;

import com.algaworks.algafood_api.api.v1.model.RestauranteModel;
import com.algaworks.algafood_api.domain.model.Restaurante;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Bean;

@Mapper(componentModel = "spring")
public interface RestauranteMapper {

    @Bean
    @Mapping(source = "endereco.cidade.estado.nome" , target = "endereco.cidade.estado")
    RestauranteModel toModel(Restaurante restaurante);

}
