package com.algaworks.algafood_api.api.v1.openapi.controller;

import com.algaworks.algafood_api.api.v1.model.DTO.ProdutoDTO;
import com.algaworks.algafood_api.api.v1.model.ProdutoModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

@Tag(name = "Produtos")
@SecurityRequirement(name = "security_auth")
public interface RestauranteProdutoControllerOpenApi {

    @Operation(summary = "Lista todos os produtos de um restaurante", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404" ,
                    description = "Restaurante não encontrado" ,
                    content = @Content(schema = @Schema(ref = "ApiError"))),
            @ApiResponse(responseCode = "400" ,
                    description = "Erro no id do restaurante" ,
                    content = @Content(schema = @Schema(ref = "ApiError")))})
    CollectionModel<ProdutoModel> pegarTodosDeUmRestaurante(
            @Parameter(example = "1" , description = "Id do restaurante" , required = true)Long restauranteId,
            @Parameter(example = "false" , description = "Incluir inativos" , required = false)Boolean incluirInativos);

    @Operation(summary = "Pega um produto por id de um restaurante por id", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404" ,
                    description = "Restaurante ou produto não encontrado" ,
                    content = @Content(schema = @Schema(ref = "ApiError"))),
            @ApiResponse(responseCode = "400" ,
                    description = "Erro no id do restaurante ou do produto" ,
                    content = @Content(schema = @Schema(ref = "ApiError")))})
    ProdutoModel pegarUnico(
            @Parameter(example = "1" , description = "Id do restaurante" , required = true)Long restauranteId,
            @Parameter(example = "1" , description = "Id do produto" , required = true)Long produtoId);

    @Operation(summary = "Cadastra um produto a um restaurante por id", responses = {
            @ApiResponse(responseCode = "201", description = "Produto cadastrado"),
            @ApiResponse(responseCode = "404" ,
                    description = "Restaurante não encontrado" ,
                    content = @Content(schema = @Schema(ref = "ApiError")))})
    ProdutoModel adicionar(
            @Parameter(example = "1" , description = "Id do restaurante" , required = true)Long restauranteId,
            @RequestBody(description = "Representação de um novo produto" , required = true) ProdutoDTO produtoDTO);

    @Operation(summary = "Atualiza um produto por id de um restaurante por id", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404" ,
                    description = "Restaurante ou produto não encontrado" ,
                    content = @Content(schema = @Schema(ref = "ApiError")))})
    ProdutoModel salvar(
            @Parameter(example = "1" , description = "Id do restaurante" , required = true)Long restauranteId,
            @Parameter(example = "1" , description = "Id do produto" , required = true)Long produtoId,
            @RequestBody(description = "Representação de um produto com dados atualizados" , required = true)ProdutoDTO produtoDTO);

}
