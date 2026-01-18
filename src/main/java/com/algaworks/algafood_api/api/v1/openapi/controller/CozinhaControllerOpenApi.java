package com.algaworks.algafood_api.api.v1.openapi.controller;
import com.algaworks.algafood_api.api.v1.model.CozinhaModel;
import com.algaworks.algafood_api.api.v1.model.DTO.CozinhaDTO;
import com.algaworks.algafood_api.core.springdoc.annotations.PageableParameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

@Tag(name =  "Cozinhas")
public interface CozinhaControllerOpenApi {

    @PageableParameter
    @Operation(summary = "Lista  de  Cozinhas")
    PagedModel<CozinhaModel> all(@Parameter(hidden = true) Pageable pageable);

    @Operation(summary = "Busca uma Cozinha por id"  , responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404" ,
                    description = "Cozinha não  encontrada" ,
                    content = @Content(schema = @Schema(ref = "ApiError")))})
    CozinhaModel getById(
            @Parameter(description = "Id de uma cozinha" , example = "1"  , required = true)Long cozinhaId);

    @Operation(summary = "Cadastra uma nova cozinha"  , responses = {
            @ApiResponse(responseCode = "201" , description = "Cozinha cadastrada"),
            @ApiResponse(responseCode = "400" ,
                    description = "Erro no nome da cozinha",
                    content = @Content(schema = @Schema(ref = "ApiError")))
    })
    CozinhaModel add(
            @RequestBody(description = "Representação de uma nova cozinha", required = true)CozinhaDTO cozinhaDTO);

    @Operation(summary = "Atualiza os dados de uma cozinha por id"  , responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400" ,
                    description = "Erro no nome da cozinha",
                    content = @Content(schema = @Schema(ref = "ApiError"))),
            @ApiResponse(responseCode = "404" ,
                    description = "Cozinha não  encontrada" ,
                    content = @Content(schema = @Schema(ref = "ApiError")))
    })
    CozinhaModel save(
            @Parameter(description = "Id de uma cozinha" , example = "1"  , required = true)Long cozinhaId,
            @RequestBody(description = "Representação de uma cozinha com os dados atualizados", required = true)CozinhaDTO cozinhaDTO);

    @Operation(summary = "Remove uma cozinha por id"  , responses = {
            @ApiResponse(responseCode = "204" , description = "Cozinha removida"),
            @ApiResponse(responseCode = "404" ,
                    description = "Cozinha não  encontrada" ,
                    content = @Content(schema = @Schema(ref = "ApiError")))
    })
    ResponseEntity<Void> remove(
            @Parameter(description = "Id de uma cozinha" , example = "1"  , required = true)Long cozinhaId);

}