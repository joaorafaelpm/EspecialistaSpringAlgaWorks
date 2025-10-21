package com.algaworks.algafood_api.api.assembler.mapper;

import com.algaworks.algafood_api.api.model.UsuarioModel;
import com.algaworks.algafood_api.domain.model.Usuario;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Bean
    UsuarioModel toModel(Usuario usuario);


}

