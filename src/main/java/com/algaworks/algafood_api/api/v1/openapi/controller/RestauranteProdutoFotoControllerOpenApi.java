package com.algaworks.algafood_api.api.v1.openapi.controller;

import com.algaworks.algafood_api.api.v1.model.DTO.FotoProdutoDTO;
import com.algaworks.algafood_api.api.v1.model.FotoProdutoModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import java.io.IOException;

@Tag(name = "Produtos")
@SecurityRequirement(name = "security_auth")
public interface RestauranteProdutoFotoControllerOpenApi {

    @Operation(summary = "Atualiza a foto do produto de um restaurante", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404" ,
                    description = "Restaurante ou produto não encontrado" ,
                    content = @Content(schema = @Schema(ref = "ApiError")))})
    FotoProdutoModel adicionarFoto(@Parameter(description = "Id  do restaurante",example = "1",required = true) Long restauranteId,
                                   @Parameter(description = "Id  do produto",example = "1",required = true)Long produtoId,
                                   @RequestBody(required = true) FotoProdutoDTO fotoProdutoDTO) throws IOException;

    @Operation(summary = "Remove uma foto de um produto por id de um restaurante por id", responses = {
            @ApiResponse(responseCode = "204", description = "Foto removida"),
            @ApiResponse(responseCode = "400" ,
                    description = "Id do restaurante ou produto inválido" ,
                    content = @Content(schema = @Schema(ref = "ApiError"))),
            @ApiResponse(responseCode = "404" ,
                    description = "Restaurante ou produto não encontrado" ,
                    content = @Content(schema = @Schema(ref = "ApiError")))})
    ResponseEntity<Void> removerFoto(
            @Parameter(description = "Id  do restaurante",example = "1",required = true)Long restauranteId,
            @Parameter(description = "Id  do produto",example = "1",required = true)Long produtoId);


    @Operation(summary = "Busca a foto de um produto de um  restaurante", responses = {
            @ApiResponse(responseCode = "200"  , content = {
                    @Content(mediaType = "application/json" ,
                            schema = @Schema(implementation = FotoProdutoModel.class)),
                    @Content(mediaType = "image/jpeg",
                            schema = @Schema(type = "string" , format = "binary")),
                    @Content(mediaType = "image/png",
                            schema = @Schema(type = "string" , format = "binary"))
            }) ,
            @ApiResponse(responseCode = "404" ,
                    description = "Restaurante ou produto não encontrado" ,
                    content = @Content(schema = @Schema(ref = "ApiError")))
    })
    FotoProdutoModel pegarFoto(
            @Parameter(description = "Id  do restaurante",example = "1",required = true)Long restauranteId,
            @Parameter(description = "Id  do produto",example = "1",required = true)Long produtoId);

    @Operation(hidden = true)
    ResponseEntity<?> servirFoto(Long restauranteId, Long produtoId, String acceptHeader)
            throws HttpMediaTypeNotAcceptableException;

}
