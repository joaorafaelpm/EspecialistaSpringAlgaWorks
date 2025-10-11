package com.algaworks.algafood_api.api.model;

import com.algaworks.algafood_api.domain.model.*;
import com.algaworks.algafood_api.domain.model.enuns.StatusPedido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PedidoModel {

    private String codigo;

    private BigDecimal subtotal ;
    private BigDecimal taxaFrete ;
    private BigDecimal valorTotal ;

    private OffsetDateTime dataCriacao;
    private OffsetDateTime dataConfirmacao ;
    private OffsetDateTime dataCancelamento;
    private OffsetDateTime dataEntrega;

    private StatusPedido statusPedido ;

    private RestauranteResumoModel restaurante;

    private UsuarioModel cliente ;

    private EnderecoModel enderecoEntrega ;

    private FormaPagamentoModel formaPagamento ;

    private List<ItemPedidoModel> itens;

}
