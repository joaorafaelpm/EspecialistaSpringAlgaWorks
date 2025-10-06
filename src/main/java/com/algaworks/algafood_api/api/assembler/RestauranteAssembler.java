package com.algaworks.algafood_api.api.assembler;

import com.algaworks.algafood_api.api.model.RestauranteModel;
import com.algaworks.algafood_api.domain.model.Restaurante;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Bean;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestauranteAssembler {

//    Diferente do ModelMapper, o mapstruct é menos verboso para especificar um elemento de alguma entidade:
//    Aqui eu simplesmente substitui o elemento "taxaFrete" por "precoFrete" e ao invés de adicionar um mapping como é no modelMapper, eu só anoto a entidade com o @Mapping, bem mais prático e menos mágico, porém com resultados mais previsíveis
    @Bean
    @Mapping(source = "taxaFrete" , target = "precoFrete")
    RestauranteModel restauranteToRestauranteModel(Restaurante restaurante);

    @Bean
    List<RestauranteModel> toCollection(List<Restaurante> listaCozinha);

}
