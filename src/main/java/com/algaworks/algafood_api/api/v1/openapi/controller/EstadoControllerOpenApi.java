package com.algaworks.algafood_api.api.v1.openapi.controller;

import com.algaworks.algafood_api.api.v1.model.DTO.EstadoDTO;
import com.algaworks.algafood_api.api.v1.model.EstadoModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Tag(name = "Estados")
@SecurityRequirement(name = "security_auth")
public interface EstadoControllerOpenApi {

    @Operation(summary = "Lista de estados")
    CollectionModel<EstadoModel> all();

    @Operation(summary = "Busca um estado por id" , responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404" ,
                    description = "Estado não encontrado" ,
                    content = @Content(schema = @Schema(ref = "ApiError"))),
            @ApiResponse(responseCode = "400" ,
                    description = "Erro no id do estado" ,
                    content = @Content(schema = @Schema(ref = "ApiError")))
    })
    EstadoModel getById(
            @Parameter(description = "Id de um estado" , example = "1"  , required = true)Long estadoId);

    @Operation(summary = "Cadastra um novo estado" , responses = {
            @ApiResponse(responseCode = "201" , description = "Estado cadastrado")
    })
    EstadoModel add(
            @RequestBody(description = "Representação de um novo estado", required = true)EstadoDTO estadoDTO);

    @Operation(summary = "Atualiza as informações de um estado por id" , responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404" ,
                    description = "Estado não encontrado" ,
                    content = @Content(schema = @Schema(ref = "ApiError"))),
            @ApiResponse(responseCode = "400" ,
                    description = "Erro no id do estado" ,
                    content = @Content(schema = @Schema(ref = "ApiError")))
    })
    EstadoModel save(
            @Parameter(description = "Id de um estado" , example = "1"  , required = true)Long estadoId,
            @RequestBody(description = "Representação de um estado com dados atualizados", required = true)EstadoDTO estadoDTO);

    @Operation(summary = "Remove um estado por id" , responses = {
            @ApiResponse(responseCode = "204" , description = "Estado removido"),
            @ApiResponse(responseCode = "404" ,
                    description = "Estado não encontrado" ,
                    content = @Content(schema = @Schema(ref = "ApiError")))})
    ResponseEntity<Void> remove(
            @Parameter(description = "Id de um estado" , example = "1"  , required = true)Long estadoId);

}
