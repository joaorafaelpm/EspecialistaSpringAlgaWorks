package com.algaworks.algafood_api.api.v1.model.DTO;


import com.algaworks.algafood_api.core.validation.PositivoOuZero;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class RestauranteDTO {

    @Schema(example = "Pizzaria fredbear")
    @NotBlank
    private String nome  ;

//    Aqui eu estou especificando o required por que já que eu fiz a anotação "PositivoOuZero" de maneira personalizada, o Spring não entende que ele é obrigatório já que eu faço a verificação por meio de um if dentro da implementação, e não uma anotação @NotNull, então ele não entende
//    Eu poderia resolver esse problema simplesmente adicionando o @NotNull na anotação, porém isso iria contra a proposta do exercício de ter uma anotação personalizada, então eu vou simplesmente anotar como required
    @Schema(example = "10.10" , requiredMode = RequiredMode.REQUIRED)
    @PositivoOuZero
    private BigDecimal taxaFrete  ;

    @Valid
    @NotNull
    private CozinhaIdDTO cozinhaId;

    @Valid
    @NotNull
    private EnderecoDTO endereco;

}
