package com.algaworks.algafood_api.api.model.input;

import com.algaworks.algafood_api.core.validation.PositivoOuZero;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class CidadeDTO {

    @NotBlank
    private String nome  ;

    @Valid
    @NotNull
    private EstadoIdDTO estadoId;

}
