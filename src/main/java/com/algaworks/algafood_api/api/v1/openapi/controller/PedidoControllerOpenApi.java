package com.algaworks.algafood_api.api.v1.openapi.controller;

import com.algaworks.algafood_api.api.v1.model.DTO.PedidoDTO;
import com.algaworks.algafood_api.api.v1.model.PedidoModel;
import com.algaworks.algafood_api.api.v1.model.PedidoResumoModel;
import com.algaworks.algafood_api.core.springdoc.annotations.PageableParameter;
import com.algaworks.algafood_api.core.springdoc.annotations.PedidoFilterAnnotation;
import com.algaworks.algafood_api.domain.filter.PedidoFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Pedidos")
public interface PedidoControllerOpenApi {

    @PageableParameter
    @PedidoFilterAnnotation
    @Operation(summary = "Lista de pedidos")
    PagedModel<PedidoResumoModel> pesquisar(@Parameter(hidden = true)PedidoFilter pedidoFilter,
                                            @Parameter(hidden = true) Pageable pageable);

    @Operation(summary = "Cadastra um novo pedido"  , responses = {
            @ApiResponse(responseCode = "201" , description = "Pedido cadastrado")})
    PedidoModel salvar(
            @RequestBody(description = "Representação de um novo pedido", required = true) PedidoDTO pedidoDTO);

    @Operation(summary = "Pega um único pedido pelo código"  , responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404" ,
                    description = "Pedido não encontrado" ,
                    content = @Content(schema = @Schema(ref = "ApiError")))
    })
    PedidoModel pegarUm(
            @Parameter(description = "Código de um pedido", example = "936dc9ec-05bf-44e5-8c07-7e51adc6083d", required = true)String codigoPedido);

}
