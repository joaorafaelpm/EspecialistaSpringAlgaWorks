package com.algaworks.algafood_api.api.v1.openapi.controller;

import com.algaworks.algafood_api.api.v1.model.GrupoModel;
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

@Tag(name = "Usuários")
@SecurityRequirement(name = "security_auth")
public interface UsuarioGrupoControllerOpenApi {

    @Operation(summary = "Lista todas os grupos de um usuário por id", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404" ,
                    description = "Usuário não encontrado" ,
                    content = @Content(schema = @Schema(ref = "ApiError")))})
    CollectionModel<GrupoModel> pegarTodosGruposDeUmUsuario(
            @Parameter(description = "Id do usuário" , example = "1" , required = true)Long usuarioId);

    @Operation(summary = "Desassocia um grupo por id de um usuário por id", responses = {
            @ApiResponse(responseCode = "204" , description = "Grupo desassociada"),
            @ApiResponse(responseCode = "404" ,
                    description = "Grupo ou usuário não encontrado" ,
                    content = @Content(schema = @Schema(ref = "ApiError")))})
    ResponseEntity<Void> desassociar(
             @Parameter(description = "Id do usuário" , example = "1" , required = true)Long usuarioId,
             @Parameter(description = "Id do grupo" , example = "1" , required = true)Long grupoId);

    @Operation(summary = "Associa um grupo por id de um usuário por id", responses = {
            @ApiResponse(responseCode = "204" , description = "Grupo associado"),
            @ApiResponse(responseCode = "404" ,
                    description = "Grupo ou usuário não encontrado" ,
                    content = @Content(schema = @Schema(ref = "ApiError")))})
    ResponseEntity<Void> associar(
            @Parameter(description = "Id do usuário" , example = "1" , required = true)Long usuarioId,
            @Parameter(description = "Id do grupo" , example = "1" , required = true)Long grupoId);

}
