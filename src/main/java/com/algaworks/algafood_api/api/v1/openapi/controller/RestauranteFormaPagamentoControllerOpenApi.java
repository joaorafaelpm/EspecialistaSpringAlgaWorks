package com.algaworks.algafood_api.api.v1.openapi.controller;

import com.algaworks.algafood_api.api.v1.model.FormaPagamentoModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
@Tag(name = "Restaurantes")
@SecurityRequirement(name = "security_auth")
public interface RestauranteFormaPagamentoControllerOpenApi {

    @Operation(summary = "Lista as formas de pagamento por id de um restaurante", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404" ,
                    description = "Restaurante não encontrado" ,
                    content = @Content(schema = @Schema(ref = "ApiError")))})
    CollectionModel<FormaPagamentoModel> listar(
            @Parameter(example = "1" , description = "Id do restaurante", required = true) Long restauranteId);

    @Operation(summary = "Desassocia uma forma de pagamento a um restaurante", responses = {
            @ApiResponse(responseCode = "204" , description = "Forma de pagamento desassociada"),
            @ApiResponse(responseCode = "404" ,
                    description = "Restaurante ou forma de pagamento não encontrado" ,
                    content = @Content(schema = @Schema(ref = "ApiError")))})
    ResponseEntity<Void> desassociar(
            @Parameter(example = "1" , description = "Id do restaurante", required = true)Long restauranteId,
            @Parameter(example = "1" , description = "Id da forma de pagamento", required = true)Long formaPagamentoId);

    @Operation(summary = "Associa uma forma de pagamento a um restaurante", responses = {
            @ApiResponse(responseCode = "204" , description = "Forma de pagamento associada"),
            @ApiResponse(responseCode = "404" ,
                    description = "Restaurante ou forma de pagamento não encontrado" ,
                    content = @Content(schema = @Schema(ref = "ApiError")))})
    ResponseEntity<Void> associar(
            @Parameter(example = "1" , description = "Id do restaurante", required = true)Long restauranteId,
            @Parameter(example = "1" , description = "Id da forma de pagamento", required = true)Long formaPagamentoId);

}