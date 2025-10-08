package com.algaworks.algafood_api.api.assembler;

import com.algaworks.algafood_api.api.model.RestauranteModel;
import com.algaworks.algafood_api.domain.model.Restaurante;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Bean;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestauranteAssembler {

    @Bean
    @Mapping(source = "endereco.cidade.estado.nome" , target = "endereco.cidade.estado")
    RestauranteModel restauranteToRestauranteModel(Restaurante restaurante);

    @Bean
    List<RestauranteModel> toCollection(List<Restaurante> listaFormaPagamento);

}
