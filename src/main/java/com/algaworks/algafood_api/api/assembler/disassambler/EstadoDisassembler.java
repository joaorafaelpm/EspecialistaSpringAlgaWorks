package com.algaworks.algafood_api.api.assembler.disassambler;

import com.algaworks.algafood_api.api.model.DTO.EstadoDTO;
import com.algaworks.algafood_api.domain.model.Estado;
import org.mapstruct.*;
import org.springframework.context.annotation.Bean;

@Mapper(componentModel = "spring")
public interface EstadoDisassembler {

    @Bean
    Estado estadoDTOToEstado (EstadoDTO estadoDTO) ;

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateEstadoFromDto(EstadoDTO dto, @MappingTarget Estado entity);

}
