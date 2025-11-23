package com.algaworks.algafood_api.api.v2.assembler.disassembler;

import com.algaworks.algafood_api.api.v2.model.DTO.CozinhaDTOV2;
import com.algaworks.algafood_api.domain.model.Cozinha;
import org.mapstruct.*;
import org.springframework.context.annotation.Bean;

@Mapper(componentModel = "spring")
public interface CozinhaDisassemblerV2 {

    @Bean
    @Mapping(source = "nomeCozinha" , target = "nome")
    Cozinha cozinhaDTOToCozinha (CozinhaDTOV2 cozinhaDTO) ;

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateCozinhaFromDto(CozinhaDTOV2 dto, @MappingTarget Cozinha entity);

}

