package com.algaworks.algafood_api.api.v1.assembler.disassambler;

import com.algaworks.algafood_api.api.v1.model.DTO.GrupoDTO;
import com.algaworks.algafood_api.domain.model.Grupo;
import org.mapstruct.*;
import org.springframework.context.annotation.Bean;


@Mapper(componentModel = "spring")
public interface GrupoDisassembler {

    @Bean
    Grupo grupoDTOToGrupo (GrupoDTO grupoDTO) ;

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateGrupoFromDto(GrupoDTO dto, @MappingTarget Grupo entity);


}
