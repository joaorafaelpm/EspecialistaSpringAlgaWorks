package com.algaworks.algafood_api.api.v1.model.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CidadeDTO {

//    Vou deixar de referencia, por que geralmente seria necessário  adicionar o requiredMode desta forma, porém o swagger de hoje  em dia já interpreta  o "@NotNull" passando como algo obrigatório
//    @Schema(example = "Campinas", requiredMode = RequiredMode.REQUIRED)
    @Schema(example = "Campinas")
    @NotBlank
    private String nome  ;

    @Valid
    @NotNull
    private EstadoIdDTO estadoId;

}
