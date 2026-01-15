package com.algaworks.algafood_api.api.v1.openapi.controller;

import com.algaworks.algafood_api.api.v1.model.DTO.SenhaDTO;
import com.algaworks.algafood_api.api.v1.model.DTO.UsuarioComSenhaDTO;
import com.algaworks.algafood_api.api.v1.model.DTO.UsuarioDTO;
import com.algaworks.algafood_api.api.v1.model.UsuarioModel;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;


@SecurityRequirement(name = "security_auth")
public interface UsuarioControllerOpenApi {

    CollectionModel<UsuarioModel> all();

    UsuarioModel findById(Long usuarioId);

    UsuarioModel add(UsuarioComSenhaDTO usuarioDTO);

    UsuarioModel save(Long usuarioId, UsuarioDTO usuarioDTO);

    ResponseEntity<Void> savePassword(Long usuarioId, SenhaDTO senhaDTO);

}
