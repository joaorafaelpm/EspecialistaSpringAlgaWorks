package com.algaworks.algafood_api.api.v1.model.DTO;

import com.algaworks.algafood_api.core.validation.PositivoOuZero;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ItemPedidoDTO {

    @Schema(example = "1")
    @NotNull
    private Long produtoId ;

    @Schema(example = "1")
    @PositivoOuZero
    private Integer quantidade ;

    @Schema(example = "Sem pimenta, por favor")
    private String observacao;

}
