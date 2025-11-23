package com.algaworks.algafood_api.api.v1.assembler.mapper;

import com.algaworks.algafood_api.api.v1.model.PermissaoModel;
import com.algaworks.algafood_api.domain.model.Permissao;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PermissaoMapper {

    @Bean
    PermissaoModel toModel(Permissao permissao);

    @Bean
    List<PermissaoModel> toCollection(Collection<Permissao> listaPermissao);

}
