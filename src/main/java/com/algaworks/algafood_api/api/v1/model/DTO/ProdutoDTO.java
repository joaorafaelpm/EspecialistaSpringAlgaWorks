package com.algaworks.algafood_api.api.v1.model.DTO;

import com.algaworks.algafood_api.core.validation.PositivoOuZero;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class ProdutoDTO {

    @NotBlank
    private String nome ;
    @NotBlank
    private String descricao ;

    @PositivoOuZero
    private BigDecimal preco ;

    @NotNull
    private Boolean ativo = false;

}
