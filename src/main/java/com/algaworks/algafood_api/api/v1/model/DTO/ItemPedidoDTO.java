package com.algaworks.algafood_api.api.v1.model.DTO;

import com.algaworks.algafood_api.core.validation.PositivoOuZero;
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

    @NotNull
    private Long produtoId ;

    @PositivoOuZero
    private Integer quantidade ;

    private String observacao;

}
