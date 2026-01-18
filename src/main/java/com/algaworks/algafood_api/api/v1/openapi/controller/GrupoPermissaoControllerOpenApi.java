package com.algaworks.algafood_api.api.v1.openapi.controller;

import com.algaworks.algafood_api.api.v1.model.PermissaoModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Tag(name = "Grupos")
@SecurityRequirement(name = "security_auth")
public interface GrupoPermissaoControllerOpenApi {

    @Operation(summary = "Lista todas as permissões de um grupo por id", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404" ,
                    description = "Grupo não encontrado" ,
                    content = @Content(schema = @Schema(ref = "ApiError")))})
    CollectionModel<PermissaoModel> listarPermissao(
            @Parameter(description = "Id do grupo" , example = "1" , required = true) Long grupoId);

    @Operation(summary = "Desassocia uma permissão por id de um grupo por id", responses = {
            @ApiResponse(responseCode = "204" , description = "Permissão desassociada"),
            @ApiResponse(responseCode = "404" ,
                    description = "Grupo ou permissão não encontrado" ,
                    content = @Content(schema = @Schema(ref = "ApiError")))})
    ResponseEntity<Void> desassociarPermissao(
            @Parameter(description = "Id do grupo" , example = "1" , required = true) Long grupoId ,
            @Parameter(description = "Id da permissão" , example = "1" , required = true)Long permissaoId);

    @Operation(summary = "Associa uma permissão por id de um grupo por id", responses = {
            @ApiResponse(responseCode = "204" , description = "Permissão associada"),
            @ApiResponse(responseCode = "404" ,
                    description = "Grupo ou permissão não encontrado" ,
                    content = @Content(schema = @Schema(ref = "ApiError")))})
    ResponseEntity<Void> associarPermissao(
            @Parameter(description = "Id do grupo" , example = "1" , required = true) Long grupoId ,
            @Parameter(description = "Id da permissão" , example = "1" , required = true)Long permissaoId);

}
