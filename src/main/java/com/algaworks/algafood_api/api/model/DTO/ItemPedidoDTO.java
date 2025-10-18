package com.algaworks.algafood_api.api.model.DTO;

import com.algaworks.algafood_api.core.validation.PositivoOuZero;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ItemPedidoDTO {

    @NotNull
    private Long produtoId ;

    @PositivoOuZero
    private Integer quantidade ;

    private String observacao;

}
