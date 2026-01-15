package com.algaworks.algafood_api.api.v1.openapi.controller;

import com.algaworks.algafood_api.api.v1.model.DTO.GrupoDTO;
import com.algaworks.algafood_api.api.v1.model.GrupoModel;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
public interface GrupoControllerOpenApi {

    CollectionModel<GrupoModel> all();

    GrupoModel getById(Long grupoId);

    GrupoModel add(GrupoDTO grupoDTO);

    GrupoModel save(Long grupoId,GrupoDTO grupoDTO);

    ResponseEntity<Void> remove(Long grupoId);

}
