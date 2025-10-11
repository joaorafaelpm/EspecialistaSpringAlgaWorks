package com.algaworks.algafood_api.api.model.input;

import com.algaworks.algafood_api.api.model.EnderecoModel;
import com.algaworks.algafood_api.api.model.FormaPagamentoModel;
import com.algaworks.algafood_api.api.model.ItemPedidoModel;
import com.algaworks.algafood_api.core.validation.PositivoOuZero;
import com.algaworks.algafood_api.domain.model.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PedidoDTO {

    @Valid
    @NotNull
    private RestauranteIdDTO restauranteId;

    @Valid
    @NotNull
    private UsuarioIdDTO clienteId ;

    @Valid
    @NotNull
    private FormaPagamentoIdDTO formaPagamentoId ;

    @Valid
    @NotNull
    private EnderecoDTO enderecoEntrega ;

    @Valid
    @Size(min = 1)
    @NotNull
    private List<ItemPedidoDTO> itens;

}

