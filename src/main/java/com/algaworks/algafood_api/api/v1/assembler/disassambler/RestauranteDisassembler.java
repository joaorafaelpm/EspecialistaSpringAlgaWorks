package com.algaworks.algafood_api.api.v1.assembler.disassambler;

import com.algaworks.algafood_api.api.v1.model.DTO.RestauranteDTO;
import com.algaworks.algafood_api.domain.model.Restaurante;
import org.mapstruct.*;
import org.springframework.context.annotation.Bean;


//Eu separo as classes entre assembler e diassembler mais por uma questão de organização mesmo, no caso do mapstruct que é necessário fazer vários default functions para os mapeamentos eu senti que ficaria muito código com funções diferentes e decidi separar
@Mapper(componentModel = "spring")
public interface RestauranteDisassembler {

    @Bean
    @Mapping(source = "cozinhaId", target = "cozinha" )
    Restaurante restauranteDTOToRestaurante (RestauranteDTO restauranteDTO) ;

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateRestauranteFromDto(RestauranteDTO dto, @MappingTarget Restaurante entity);


}
