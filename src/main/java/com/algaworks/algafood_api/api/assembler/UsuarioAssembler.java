package com.algaworks.algafood_api.api.assembler;

import com.algaworks.algafood_api.api.model.UsuarioModel;
import com.algaworks.algafood_api.domain.model.Usuario;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Bean;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioAssembler {

    @Bean
    UsuarioModel usuarioToUsuarioModel(Usuario usuario);

    @Bean
    List<UsuarioModel> toCollection(List<Usuario> listaUsuario);

}

