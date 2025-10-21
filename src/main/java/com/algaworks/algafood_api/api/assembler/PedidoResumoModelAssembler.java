package com.algaworks.algafood_api.api.assembler;

import com.algaworks.algafood_api.api.AlgaLinks;
import com.algaworks.algafood_api.api.assembler.mapper.PedidoMapper;
import com.algaworks.algafood_api.api.assembler.mapper.PedidoResumoMapper;
import com.algaworks.algafood_api.api.controller.*;
import com.algaworks.algafood_api.api.model.PedidoResumoModel;
import com.algaworks.algafood_api.domain.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class PedidoResumoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel> {

    @Autowired
    private PedidoResumoMapper pedidoResumoMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public PedidoResumoModelAssembler() {
        super(PedidoController.class, PedidoResumoModel.class);
    }

    @Override
    public PedidoResumoModel toModel(Pedido pedido) {
        PedidoResumoModel pedidoModel = pedidoResumoMapper.toModel(pedido);

        pedidoModel.add(algaLinks.linkToPedidos(IanaLinkRelations.COLLECTION.value()));

        pedidoModel.getRestaurante().add(algaLinks.linkToRestaurante(pedidoModel.getRestaurante().getId()));
        pedidoModel.getCliente().add(algaLinks.linkToUsuario(pedidoModel.getCliente().getId()));

        pedidoModel.add(algaLinks.linkToPedido(pedidoModel.getCodigo()));

        return pedidoModel;
    }

    public CollectionModel<PedidoResumoModel> toCollection (Collection<Pedido> listaPedido) {
        var listaPedidoModel = listaPedido.stream().map(this::toModel).toList();
        CollectionModel<PedidoResumoModel> pedidosCollectionModel = CollectionModel.of(listaPedidoModel);
        pedidosCollectionModel.add(algaLinks.linkToPedidos("pedidos"));

        return pedidosCollectionModel;
    }
}
