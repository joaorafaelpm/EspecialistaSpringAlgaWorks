package com.algaworks.algafood_api.api.v1.openapi.controller;

import com.algaworks.algafood_api.api.v1.model.EstatisticasModel;
import com.algaworks.algafood_api.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood_api.domain.model.dto.VendaDiaria;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SecurityRequirement(name = "security_auth")
public interface EstatisticasControllerOpenApi {

    EstatisticasModel estatisticas();

    List<VendaDiaria> consultarVendasDiarias(
            VendaDiariaFilter filtro, String timeOffset);

    ResponseEntity<byte[]> consultarVendasDiariasPdf(
            VendaDiariaFilter filtro,
            String timeOffset);

}