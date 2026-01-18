package com.algaworks.algafood_api.api.v1.openapi.controller;

import com.algaworks.algafood_api.api.v1.model.DTO.FormaPagamentoDTO;
import com.algaworks.algafood_api.api.v1.model.FormaPagamentoModel;
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
import org.springframework.web.context.request.ServletWebRequest;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Formas De Pagamento")
public interface FormaPagamentoControllerOpenApi {

    @Operation(description = "Lista formas de pagamento")
    ResponseEntity<CollectionModel<FormaPagamentoModel>> all(@Parameter(hidden = true) ServletWebRequest request);

    @Operation(summary = "Busca uma forma de pagamento por id"  , responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404" ,
                    description = "forma de pagamento não  encontrada" ,
                    content = @Content(schema = @Schema(ref = "ApiError")))})
    ResponseEntity<FormaPagamentoModel> getById(
            @Parameter(description = "Id de uma forma de pagamento", example = "1" , required = true) Long formaPagamentoId,
            @Parameter(hidden = true) ServletWebRequest request);

    @Operation(summary = "Busca uma forma de pagamento por id"  , responses = {
            @ApiResponse(responseCode = "201" , description = "Forma de pagamento cadastrada")})
    FormaPagamentoModel add(
            @RequestBody(description = "Representação de uma nova forma de pagamento", required = true)FormaPagamentoDTO formaPagamentoDTO);

    @Operation(summary = "Atualiza os dados de uma forma de pagamento por id"  , responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400" ,
                    description = "Erro no nome da forma de pagamento",
                    content = @Content(schema = @Schema(ref = "ApiError"))),
            @ApiResponse(responseCode = "404" ,
                    description = "Forma de pagamento não encontrada" ,
                    content = @Content(schema = @Schema(ref = "ApiError")))
    })
    FormaPagamentoModel save(
            @Parameter(description = "Id de uma forma de pagamento", example = "1" , required = true) Long formaPagamentoId,
         @RequestBody(description = "Representação de uma forma de pagamento com os dados atualizados", required = true) FormaPagamentoDTO formaPagamentoDTO);

    @Operation(summary = "Remove uma forma de pagamento por id"  , responses = {
            @ApiResponse(responseCode = "204" , description = "Forma de pagamento removida"),
            @ApiResponse(responseCode = "404" ,
                    description = "Forma de pagamento não encontrada" ,
                    content = @Content(schema = @Schema(ref = "ApiError")))
    })
    ResponseEntity<Void> remove(
            @Parameter(description = "Id de uma forma de pagamento", example = "1" , required = true) Long formaPagamentoId);

}