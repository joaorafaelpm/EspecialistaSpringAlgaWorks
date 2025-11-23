package com.algaworks.algafood_api.api.v1.model.DTO;


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
public class RestauranteDTO {

    @NotBlank
    private String nome  ;

    @PositivoOuZero
    private BigDecimal taxaFrete  ;

    @Valid
    @NotNull
    private CozinhaIdDTO cozinhaId;

    @Valid
    @NotNull
    private EnderecoDTO endereco;

}
