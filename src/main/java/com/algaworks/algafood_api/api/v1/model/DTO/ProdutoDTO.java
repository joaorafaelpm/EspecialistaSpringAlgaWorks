package com.algaworks.algafood_api.api.v1.model.DTO;

import com.algaworks.algafood_api.core.validation.PositivoOuZero;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
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

public class ProdutoDTO {

    @Schema(example = "Porco com molho agridoce")
    @NotBlank
    private String nome ;

    @Schema(example = "Deliciosa carne suína ao molho especial")
    @NotBlank
    private String descricao ;

    @Schema(example = "78.90" , requiredMode = RequiredMode.REQUIRED)
    @PositivoOuZero
    private BigDecimal preco ;

    @Schema(example = "false")
    @NotNull
    private Boolean ativo = false;

}
