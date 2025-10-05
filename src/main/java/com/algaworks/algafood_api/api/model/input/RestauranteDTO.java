package com.algaworks.algafood_api.api.model.input;

import com.algaworks.algafood_api.core.validation.Groups;
import com.algaworks.algafood_api.core.validation.PositivoOuZero;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.ConvertGroup;
import jakarta.validation.groups.Default;
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
    private CozinhaIdDTO cozinha;

}
