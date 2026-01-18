package com.algaworks.algafood_api.api.v1.openapi.controller;

import com.algaworks.algafood_api.api.v1.model.DTO.GrupoDTO;
import com.algaworks.algafood_api.api.v1.model.GrupoModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Tag(name =  "Grupos")
public interface GrupoControllerOpenApi {

    @Operation(summary = "Lista  de  Grupos")
    CollectionModel<GrupoModel> all();

    @Operation(summary = "Busca um Grupo por id"  , responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404" ,
                    description = "Grupo não  encontrado" ,
                    content = @Content(schema = @Schema(ref = "ApiError")))})
    GrupoModel getById(
            @Parameter(description = "Id de um grupo" , example = "1"  , required = true) Long grupoId);

    @Operation(summary = "Cadastro de um grupo" , responses = {
            @ApiResponse(responseCode = "201" , description = "Grupo cadastrado"),
            @ApiResponse(responseCode = "400" ,
                    description = "Nome do grupo inválido",
                    content = @Content(schema = @Schema(ref = "ApiError")))
    })
    GrupoModel add(
            @RequestBody(description = "Representação de um novo grupo", required = true) GrupoDTO grupoDTO);

    @Operation(summary = "Atualiza um grupo por id"  , responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404" ,
                    description = "Grupo não  encontrado" ,
                    content = @Content(schema = @Schema(ref = "ApiError"))),
            @ApiResponse(responseCode = "400" ,
                    description = "Nome do grupo inválido",
                    content = @Content(schema = @Schema(ref = "ApiError")))
    })
    GrupoModel save(
                @Parameter(description = "Id de um grupo" , example = "1"  , required = true)Long grupoId,
                @RequestBody(description = "Representação de um grupo com dados atualizados", required = true)GrupoDTO grupoDTO);

    @Operation(summary = "Remove um grupo por  id"  , responses = {
            @ApiResponse(responseCode = "204" , description = "Grupo removido"),
            @ApiResponse(responseCode = "404" ,
                    description = "Grupo não  encontrado" ,
                    content = @Content(schema = @Schema(ref = "ApiError")))
    })
    ResponseEntity<Void> remove(
            @Parameter(description = "Id de um grupo" , example = "1"  , required = true) Long grupoId);

}
