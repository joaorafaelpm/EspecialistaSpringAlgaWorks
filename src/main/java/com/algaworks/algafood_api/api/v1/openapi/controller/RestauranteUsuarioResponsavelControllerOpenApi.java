package com.algaworks.algafood_api.api.v1.openapi.controller;

import com.algaworks.algafood_api.api.v1.model.UsuarioModel;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
public interface RestauranteUsuarioResponsavelControllerOpenApi {

    CollectionModel<UsuarioModel> listar(Long restauranteId);

    ResponseEntity<Void> desassociar(Long restauranteId, Long usuarioId);

    ResponseEntity<Void> associar(Long restauranteId,Long usuarioId);

}
