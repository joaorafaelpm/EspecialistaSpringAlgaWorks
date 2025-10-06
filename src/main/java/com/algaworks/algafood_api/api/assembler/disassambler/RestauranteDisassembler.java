package com.algaworks.algafood_api.api.assembler.disassambler;

import com.algaworks.algafood_api.api.model.input.RestauranteDTO;
import com.algaworks.algafood_api.domain.model.Cozinha;
import com.algaworks.algafood_api.domain.model.Restaurante;
import org.mapstruct.*;
import org.springframework.context.annotation.Bean;


//Eu separo as classes entre assembler e diassembler mais por uma questão de organização mesmo, no caso do mapstruct que é necessário fazer vários default functions para os mapeamentos eu senti que ficaria muito código com funções diferentes e decidi separar
@Mapper(componentModel = "spring")
public interface RestauranteDisassembler {

    @Bean
//    @Mapping(source = "cozinhaId", target = "cozinha" ,  qualifiedByName = "mapCozinha")
    @Mapping(source = "cozinhaId", target = "cozinha" )
    Restaurante restauranteDTOToRestaurante (RestauranteDTO restauranteDTO) ;

//    No caso de uma atualização de uma classe para a outra, a gente usa isso para prevenir que a classe venha com elementos que a gente não especifica e então não retorne eles como nulo
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//     Um exemplo de como deixar um campo inalterável durante a geração da nova classe de domínio
    @Mapping(target = "id", ignore = true)
    void updateRestauranteFromDto(RestauranteDTO dto, @MappingTarget Restaurante entity);


}
