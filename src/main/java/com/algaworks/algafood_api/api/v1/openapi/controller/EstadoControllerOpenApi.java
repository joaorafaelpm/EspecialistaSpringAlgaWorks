package com.algaworks.algafood_api.api.v1.openapi.controller;

import com.algaworks.algafood_api.api.v1.model.DTO.EstadoDTO;
import com.algaworks.algafood_api.api.v1.model.EstadoModel;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
public interface EstadoControllerOpenApi {

    CollectionModel<EstadoModel> all();

    EstadoModel getById(Long estadoId);

    EstadoModel add(EstadoDTO estadoDTO);

    EstadoModel save(Long estadoId,EstadoDTO estadoDTO);

    ResponseEntity<Void> remove(Long estadoId);

}
