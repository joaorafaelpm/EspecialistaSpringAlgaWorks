package com.algaworks.algafood_api.api.v1.assembler.mapper;

import com.algaworks.algafood_api.api.v1.model.UsuarioModel;
import com.algaworks.algafood_api.domain.model.Usuario;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Bean;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Bean
    UsuarioModel toModel(Usuario usuario);


}

