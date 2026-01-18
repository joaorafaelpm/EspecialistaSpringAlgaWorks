package com.algaworks.algafood_api.domain.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@Setter
@Getter
public class VendaDiaria {

    @Schema(example = "01/09/2026Z")
    private Date data;
    @Schema(example = "5")
    private Long totalVendas ;
    @Schema(example = "540.10")
    private BigDecimal totalFaturado ;

}
