package com.algaworks.algafood_api.api.v1.model;

import com.algaworks.algafood_api.domain.model.enuns.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Relation(collectionRelation = "pedidos")
@Getter
@Setter
@AllArgsConstructor
public class PedidoResumoModel extends RepresentationModel<PedidoResumoModel> {

    private String codigo;

    private BigDecimal subtotal ;
    private BigDecimal taxaFrete ;
    private BigDecimal valorTotal ;
    private OffsetDateTime dataCriacao;
    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataCancelamento;
    private OffsetDateTime dataEntrega;
    private StatusPedido statusPedido ;
    private RestauranteApenasNomeModel restaurante;
    private UsuarioModel cliente ;

}
