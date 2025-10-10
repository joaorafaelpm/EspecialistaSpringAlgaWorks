package com.algaworks.algafood_api.api.assembler;

import com.algaworks.algafood_api.api.model.GrupoModel;
import com.algaworks.algafood_api.domain.model.Grupo;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface GrupoAssembler {

    @Bean
    GrupoModel grupoToGrupoModel(Grupo grupo);

    @Bean
    List<GrupoModel> toCollection(Collection<Grupo> listaGrupo);

}
