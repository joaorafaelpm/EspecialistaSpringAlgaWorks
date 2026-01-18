package com.algaworks.algafood_api.api.v1.openapi.controller;

import com.algaworks.algafood_api.api.v1.model.UsuarioModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Restaurantes")
public interface RestauranteUsuarioResponsavelControllerOpenApi {

    @Operation(summary = "Lista os usuários responsáveis de um restaurante por id", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404" ,
                    description = "Restaurante não encontrado" ,
                    content = @Content(schema = @Schema(ref = "ApiError")))})
    CollectionModel<UsuarioModel> listar(
            @Parameter(example = "1" , description = "Id do restaurante" , required = true) Long restauranteId);

    @Operation(summary = "Desassociação de um usuário a um restaurante por id", responses = {
            @ApiResponse(responseCode = "204", description = "Usuário desassociado"),
            @ApiResponse(responseCode = "404" ,
                    description = "Restaurante ou usuário não encontrado" ,
                    content = @Content(schema = @Schema(ref = "ApiError")))})
    ResponseEntity<Void> desassociar(
            @Parameter(example = "1" , description = "Id do restaurante" , required = true)Long restauranteId,
            @Parameter(example = "1" , description = "Id do usuário" , required = true)Long usuarioId);

    @Operation(summary = "Associação de um usuário a um restaurante por id", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404" ,
                    description = "Restaurante ou usuário não encontrado" ,
                    content = @Content(schema = @Schema(ref = "ApiError")))})
    ResponseEntity<Void> associar(
            @Parameter(example = "1" , description = "Id do restaurante" , required = true)Long restauranteId,
            @Parameter(example = "1" , description = "Id do usuário" , required = true)Long usuarioId);

}
