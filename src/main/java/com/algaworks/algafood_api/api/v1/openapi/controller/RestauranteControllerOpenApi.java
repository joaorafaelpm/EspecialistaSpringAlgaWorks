package com.algaworks.algafood_api.api.v1.openapi.controller;

import com.algaworks.algafood_api.api.v1.model.DTO.RestauranteDTO;
import com.algaworks.algafood_api.api.v1.model.RestauranteApenasNomeModel;
import com.algaworks.algafood_api.api.v1.model.RestauranteModel;
import com.algaworks.algafood_api.api.v1.model.RestauranteResumoModel;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SecurityRequirement(name = "security_auth")
public interface RestauranteControllerOpenApi {

    CollectionModel<RestauranteResumoModel> listar();

    CollectionModel<RestauranteApenasNomeModel> listarApenasNomes();

    RestauranteModel getById(Long restauranteId);

    RestauranteModel add(RestauranteDTO restauranteDTO);

    RestauranteModel save(Long restauranteId,RestauranteDTO restauranteDTO);

    ResponseEntity<Void> ativar(Long restauranteId);

    ResponseEntity<Void> inativar(Long restauranteId);

    ResponseEntity<Void> ativarMultiplos(List<Long> restauranteIds);

    ResponseEntity<Void> inativarMultiplos(List<Long> restauranteIds);

    ResponseEntity<Void> abrir(Long restauranteId);

    ResponseEntity<Void> fechar(Long restauranteId);

}