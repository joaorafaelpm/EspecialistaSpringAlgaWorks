package com.algaworks.algafood_api.api.v2.assembler.disassembler;

import com.algaworks.algafood_api.api.v2.model.DTO.CidadeDTOV2;
import com.algaworks.algafood_api.domain.model.Cidade;
import org.mapstruct.*;
import org.springframework.context.annotation.Bean;

@Mapper(componentModel = "spring")
public interface CidadeDisassemblerV2 {

    @Bean
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estado.id", source = "idEstado")
    Cidade cidadeDTOToCidade (CidadeDTOV2 cidadeDTO) ;

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateCidadeFromDto(CidadeDTOV2 dto, @MappingTarget Cidade entity);

}
