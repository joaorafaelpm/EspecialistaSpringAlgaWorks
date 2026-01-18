package com.algaworks.algafood_api.api.v1.openapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Pedidos")
@SecurityRequirement(name = "security_auth")
public interface FluxoPedidoControllerOpenApi {

    @Operation(summary = "Confirma um pedido", responses = {
            @ApiResponse(responseCode = "204", description = "Pedido confirmado"),
            @ApiResponse(responseCode = "404" ,
                    description = "Pedido não encontrado" ,
                    content = @Content(schema = @Schema(ref = "ApiError")))
    })
    ResponseEntity<Void> confirmar(
            @Parameter(example = "936dc9ec-05bf-44e5-8c07-7e51adc6083d" , description = "Código do pedido a ser confirmado", required = true) String codigoPedido);

    @Operation(summary = "Cancela um pedido", responses = {
            @ApiResponse(responseCode = "204", description = "Pedido cancelado"),
            @ApiResponse(responseCode = "404" ,
                    description = "Pedido não encontrado" ,
                    content = @Content(schema = @Schema(ref = "ApiError")))
    })
    ResponseEntity<Void> cancelar(
            @Parameter(example = "936dc9ec-05bf-44e5-8c07-7e51adc6083d" , description = "Código do pedido a ser cancelado", required = true)String codigoPedido);

    @Operation(summary = "Entrega um pedido", responses = {
            @ApiResponse(responseCode = "204", description = "Pedido entregado"),
            @ApiResponse(responseCode = "404" ,
                    description = "Pedido não encontrado" ,
                    content = @Content(schema = @Schema(ref = "ApiError")))
    })
    ResponseEntity<Void> entregar(
            @Parameter(example = "936dc9ec-05bf-44e5-8c07-7e51adc6083d" , description = "Código do pedido a ser entregue", required = true)String codigoPedido);

}
