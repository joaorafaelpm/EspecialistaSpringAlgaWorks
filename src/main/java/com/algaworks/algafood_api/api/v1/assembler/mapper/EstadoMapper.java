package com.algaworks.algafood_api.api.v1.assembler.mapper;

import com.algaworks.algafood_api.api.v1.model.EstadoModel;
import com.algaworks.algafood_api.domain.model.Estado;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Bean;

@Mapper(componentModel = "spring")
public interface EstadoMapper {

    @Bean
    EstadoModel toModel(Estado estado);

}
