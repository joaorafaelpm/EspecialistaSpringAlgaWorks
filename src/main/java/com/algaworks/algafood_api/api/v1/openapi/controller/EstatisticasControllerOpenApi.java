package com.algaworks.algafood_api.api.v1.openapi.controller;

import com.algaworks.algafood_api.api.v1.model.EstatisticasModel;
import com.algaworks.algafood_api.api.v1.model.FotoProdutoModel;
import com.algaworks.algafood_api.core.springdoc.annotations.VendaDiariaFilterAnnotation;
import com.algaworks.algafood_api.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood_api.domain.model.dto.VendaDiaria;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Estatísticas")
@SecurityRequirement(name = "security_auth")
public interface EstatisticasControllerOpenApi {

    @Operation(hidden = true)
    EstatisticasModel estatisticas();

    @VendaDiariaFilterAnnotation
    @Operation(summary = "Consulta as vendas diárias" ,responses = {
            @ApiResponse(responseCode = "200" , content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = VendaDiaria.class))),
                    @Content(mediaType = "application/pdf",
                            schema = @Schema(type = "string", format = "binary")),
            })
    })
    List<VendaDiaria> consultarVendasDiarias(
            @Parameter(hidden = true) VendaDiariaFilter filtro,
            @Parameter(description = "Deslocamento de horário a ser considerado na consulta em relação ao UTC", schema = @Schema(type = "string", defaultValue = "+00:00"))String timeOffset);

    @Operation(hidden = true)
    ResponseEntity<byte[]> consultarVendasDiariasPdf(
            VendaDiariaFilter filtro,
            String timeOffset);

}