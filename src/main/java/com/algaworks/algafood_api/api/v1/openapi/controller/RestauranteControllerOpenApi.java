package com.algaworks.algafood_api.api.v1.openapi.controller;

import com.algaworks.algafood_api.api.v1.model.DTO.RestauranteDTO;
import com.algaworks.algafood_api.api.v1.model.RestauranteApenasNomeModel;
import com.algaworks.algafood_api.api.v1.model.RestauranteModel;
import com.algaworks.algafood_api.api.v1.model.RestauranteResumoModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import java.util.List;
@Tag(name = "Restaurantes")
@SecurityRequirement(name = "security_auth")
public interface RestauranteControllerOpenApi {

    @Operation(summary = "Lista de restaurantes", parameters = {
            @Parameter(name = "projecao" ,
                    description = "Nome da projeção",
                    example = "apenas-nome",
                    in = ParameterIn.QUERY,
                    required = false
            )
    })
    CollectionModel<RestauranteResumoModel> listar();

    @Operation(hidden = true)
    CollectionModel<RestauranteApenasNomeModel> listarApenasNomes();

    @Operation(summary = "Busca um restaurante por id"  , responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404" ,
                    description = "Restaurante não encontrado" ,
                    content = @Content(schema = @Schema(ref = "ApiError"))),
            @ApiResponse(responseCode = "400" ,
                    description = "Erro no id do restaurante",
                    content = @Content(schema = @Schema(ref = "ApiError")))
    })
    RestauranteModel getById(
            @Parameter(description = "Id de um restaurante", example = "1" , required = true)Long restauranteId);

    @Operation(summary = "Cadastra um novo restaurante"  , responses = {
            @ApiResponse(responseCode = "201" , description = "Restaurante criado")})
    RestauranteModel add(
            @RequestBody(description = "Representação de um novo restaurante", required = true)RestauranteDTO restauranteDTO);

    @Operation(summary = "Atualiza dados de um restaurante por id"  , responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404" ,
                    description = "Restaurante não encontrado" ,
                    content = @Content(schema = @Schema(ref = "ApiError"))),
            @ApiResponse(responseCode = "400" ,
                    description = "Erro no id do restaurante",
                    content = @Content(schema = @Schema(ref = "ApiError")))
    })
    RestauranteModel save(
            @Parameter(description = "Id de um restaurante", example = "1" , required = true)Long restauranteId,
            @RequestBody(description = "Representação de um restaurante com dados atualizados", required = true) RestauranteDTO restauranteDTO);

    @Operation(summary = "Ativa um restaurante por id"  , responses = {
            @ApiResponse(responseCode = "204" , description = "Restaurante ativado"),
            @ApiResponse(responseCode = "404" ,
                    description = "Restaurante não encontrado" ,
                    content = @Content(schema = @Schema(ref = "ApiError")))})
    ResponseEntity<Void> ativar(
            @Parameter(description = "Id de um restaurante", example = "1" , required = true)Long restauranteId);

    @Operation(summary = "Inativa um restaurante por id"  , responses = {
            @ApiResponse(responseCode = "204" , description = "Restaurante inativado"),
            @ApiResponse(responseCode = "404" ,
                    description = "Restaurante não encontrado" ,
                    content = @Content(schema = @Schema(ref = "ApiError")))})
    ResponseEntity<Void> inativar(
            @Parameter(description = "Id de um restaurante", example = "1" , required = true)Long restauranteId);

    @Operation(summary = "Ativa multiplos restaurante por uma lista de ids"  , responses = {
            @ApiResponse(responseCode = "204" , description = "Restaurantes ativados")})
    ResponseEntity<Void> ativarMultiplos(
            @RequestBody(description = "Lista de id dos restaurantes", required = true) List<Long> restauranteIds);

    @Operation(summary = "Inativa multiplos restaurante por uma lista de ids"  , responses = {
            @ApiResponse(responseCode = "204" , description = "Restaurantes inativados")})
    ResponseEntity<Void> inativarMultiplos(
            @RequestBody(description = "Lista de id dos restaurantes", required = true) List<Long> restauranteIds);

    @Operation(summary = "Abre um restaurante por id"  , responses = {
            @ApiResponse(responseCode = "204" , description = "Restaurantes aberto"),
            @ApiResponse(responseCode = "404" ,
                    description = "Restaurante não encontrado" ,
                    content = @Content(schema = @Schema(ref = "ApiError")))})
    ResponseEntity<Void> abrir(
            @Parameter(description = "Id de um restaurante", example = "1" , required = true) Long restauranteId);

    @Operation(summary = "Fecha um restaurante por id"  , responses = {
            @ApiResponse(responseCode = "204" , description = "Restaurantes fechado"),
            @ApiResponse(responseCode = "404" ,
                    description = "Restaurante não encontrado" ,
                    content = @Content(schema = @Schema(ref = "ApiError")))})
    ResponseEntity<Void> fechar(
            @Parameter(description = "Id de um restaurante", example = "1" , required = true) Long restauranteId);

}