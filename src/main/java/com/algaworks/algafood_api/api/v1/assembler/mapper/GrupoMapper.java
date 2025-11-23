package com.algaworks.algafood_api.api.v1.assembler.mapper;

import com.algaworks.algafood_api.api.v1.model.GrupoModel;
import com.algaworks.algafood_api.domain.model.Grupo;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface GrupoMapper {

    @Bean
    GrupoModel toModel(Grupo grupo);

    @Bean
    List<GrupoModel> toCollection(Collection<Grupo> listaGrupo);

}
