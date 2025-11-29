package com.algaworks.algafood_api.api.v1.model.DTO;

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

    @NotBlank
    private String nome  ;

    @Valid
    @NotNull
    private EstadoIdDTO estadoId;

}
