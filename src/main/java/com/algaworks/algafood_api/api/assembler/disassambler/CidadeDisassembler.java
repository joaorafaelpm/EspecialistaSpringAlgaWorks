package com.algaworks.algafood_api.api.assembler.disassambler;

import com.algaworks.algafood_api.api.model.DTO.CidadeDTO;
import com.algaworks.algafood_api.domain.model.Cidade;
import org.mapstruct.*;
import org.springframework.context.annotation.Bean;

@Mapper(componentModel = "spring")
public interface CidadeDisassembler {

    @Bean
    @Mapping(source = "estadoId", target = "estado" )
    Cidade cidadeDTOToCidade (CidadeDTO cidadeDTO) ;

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateCidadeFromDto(CidadeDTO dto, @MappingTarget Cidade entity);

}
