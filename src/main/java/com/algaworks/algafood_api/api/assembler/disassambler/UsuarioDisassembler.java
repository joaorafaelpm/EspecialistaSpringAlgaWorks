package com.algaworks.algafood_api.api.assembler.disassambler;

import com.algaworks.algafood_api.api.model.DTO.UsuarioComSenhaDTO;
import com.algaworks.algafood_api.api.model.DTO.UsuarioDTO;
import com.algaworks.algafood_api.domain.model.Usuario;
import org.mapstruct.*;
import org.springframework.context.annotation.Bean;


@Mapper(componentModel = "spring")
public interface UsuarioDisassembler {

    @Bean
    Usuario usuarioDTOToUsuario (UsuarioDTO usuarioDTO) ;

    @Bean
    @Mapping(target = "senha" , source = "senha")
    Usuario usuarioComSenhaDTOToUsuario (UsuarioComSenhaDTO usuarioComSenhaDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCadastro", ignore = true)
    void updateUsuarioFromDto(UsuarioDTO dto, @MappingTarget Usuario entity);


}

