package com.algaworks.algafood_api.api.v1.openapi.controller;

import com.algaworks.algafood_api.api.v1.model.CidadeModel;
import com.algaworks.algafood_api.api.v1.model.DTO.CidadeDTO;
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

@Tag(name =  "Cidades" , description = "Gerencia as cidades")
public interface CidadeControllerOpenApi {

    @Operation(summary = "Lista de cidades")
    CollectionModel<CidadeModel> all();

    @Operation(summary = "Busca uma Cidade por id"  , responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404" ,
                    description = "Cidade não  encontrada" ,
                    content = @Content(schema = @Schema(ref = "ApiError"))),
            @ApiResponse(responseCode = "400" ,
                    description = "Id da cidade inválido",
                    content = @Content(schema = @Schema(ref = "ApiError")))
    })
    CidadeModel getById(@Parameter(description = "Id de uma cidade" , example = "1"  , required = true) Long cidadeId);

    @Operation(summary = "Cadastra uma Cidade" ,
            description = "Cadastro de uma Cidade, necesita de um Estado e nome válido")
    CidadeModel add(@RequestBody(description = "Representação de uma nova cidade", required = true) CidadeDTO cidadeDTO);

    @Operation(summary = "Atualiza uma Cidade por Id", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404" ,
                    description = "Cidade não  encontrada" ,
                    content = @Content(schema = @Schema(ref = "ApiError"))),
            @ApiResponse(responseCode = "400" ,
                    description = "Id da cidade inválido",
                    content = @Content(schema = @Schema(ref = "ApiError")))
    })
    CidadeModel save(@Parameter(description = "Id de uma cidade" , example = "1"  , required = true) Long cidadeId,
                     @RequestBody(description = "Representação de uma cidade com dados atualizados", required = true) CidadeDTO cidadeDTO);

    @Operation(summary = "Remove uma Cidade  por Id", responses = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404" ,
                    description = "Cidade não  encontrada" ,
                    content = @Content(schema = @Schema(ref = "ApiError"))),
            @ApiResponse(responseCode = "400" ,
                    description = "Id da cidade inválido",
                    content = @Content(schema = @Schema(ref = "ApiError")))
    })
    ResponseEntity<Void> remove(@Parameter(description = "Id de uma cidade" , example = "1"  , required = true) Long cidadeId);

}
