package com.algaworks.algafood_api.api.v1.openapi.controller;

import com.algaworks.algafood_api.api.v1.model.GrupoModel;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
public interface UsuarioGrupoControllerOpenApi {

    CollectionModel<GrupoModel> pegarTodosGruposDeUmUsuario(Long usuarioId);

    ResponseEntity<Void> desassociar(Long usuarioId, Long grupoId);

    ResponseEntity<Void> associar(Long usuarioId,Long grupoId);

}
