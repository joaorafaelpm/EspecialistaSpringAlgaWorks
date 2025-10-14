package com.algaworks.algafood_api.api.assembler;

import com.algaworks.algafood_api.api.model.EstadoModel;
import com.algaworks.algafood_api.domain.model.Estado;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Bean;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EstadoAssembler {

    @Bean
    EstadoModel estadoToEstadoModel(Estado estado);

    @Bean
    List<EstadoModel> toCollection(List<Estado> listaEstados);

}
