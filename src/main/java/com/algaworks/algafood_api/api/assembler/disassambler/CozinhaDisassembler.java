package com.algaworks.algafood_api.api.assembler.disassambler;

import com.algaworks.algafood_api.api.model.DTO.CozinhaDTO;
import com.algaworks.algafood_api.domain.model.Cozinha;
import org.mapstruct.*;
import org.springframework.context.annotation.Bean;

@Mapper(componentModel = "spring")
public interface CozinhaDisassembler {

    @Bean
    Cozinha cozinhaDTOToCozinha (CozinhaDTO cozinhaDTO) ;

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateCozinhaFromDto(CozinhaDTO dto, @MappingTarget Cozinha entity);

}

