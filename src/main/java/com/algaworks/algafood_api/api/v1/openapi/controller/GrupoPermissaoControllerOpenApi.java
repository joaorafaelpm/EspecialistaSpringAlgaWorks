package com.algaworks.algafood_api.api.v1.openapi.controller;

import com.algaworks.algafood_api.api.v1.model.PermissaoModel;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
public interface GrupoPermissaoControllerOpenApi {

    CollectionModel<PermissaoModel> listarPermissao(Long grupoId);

    ResponseEntity<Void> desassociarPermissao(Long grupoId,Long permissaoId);

    ResponseEntity<Void> associarPermissao(Long grupoId, Long permissaoId);

}
