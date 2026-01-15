package com.algaworks.algafood_api.api.v1.openapi.controller;

import com.algaworks.algafood_api.api.v1.model.DTO.ProdutoDTO;
import com.algaworks.algafood_api.api.v1.model.ProdutoModel;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;

@SecurityRequirement(name = "security_auth")
public interface RestauranteProdutoControllerOpenApi {

    CollectionModel<ProdutoModel> pegarTodosDeUmRestaurante(Long restauranteId, Boolean incluirInativos);

    ProdutoModel pegarUnico(Long restauranteId,Long produtoId);

    ProdutoModel adicionar(Long restauranteId, ProdutoDTO produtoDTO);

    ProdutoModel salvar(Long restauranteId, Long produtoId, ProdutoDTO produtoDTO);

}
