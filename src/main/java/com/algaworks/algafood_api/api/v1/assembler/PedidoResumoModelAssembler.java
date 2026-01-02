package com.algaworks.algafood_api.api.v1.assembler;

import com.algaworks.algafood_api.api.v1.AlgaLinks;
import com.algaworks.algafood_api.api.v1.assembler.mapper.PedidoResumoMapper;
import com.algaworks.algafood_api.api.v1.model.PedidoResumoModel;
import com.algaworks.algafood_api.api.v1.controller.PedidoController;
import com.algaworks.algafood_api.core.security.AlgaSecurity;
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

    @Autowired
    private AlgaSecurity algaSecurity;

    public PedidoResumoModelAssembler() {
        super(PedidoController.class, PedidoResumoModel.class);
    }

    @Override
    public PedidoResumoModel toModel(Pedido pedido) {
        PedidoResumoModel pedidoModel = pedidoResumoMapper.toModel(pedido);

        Long restauranteId = pedidoModel.getRestaurante().getId();
        Long clienteId = pedido.getCliente().getId();
        if (algaSecurity.podeBuscarPedidos()) {
            pedidoModel.add(algaLinks.linkToPedidos(IanaLinkRelations.COLLECTION.value()));
            pedidoModel.add(algaLinks.linkToPedido(pedidoModel.getCodigo()));
        }
        if (algaSecurity.podeConsultarRestaurantes()) {
            pedidoModel.getRestaurante().add(algaLinks.linkToRestaurante(restauranteId));
        }
        if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
            pedidoModel.getCliente().add(algaLinks.linkToUsuario(clienteId));
        }

        return pedidoModel;
    }

    public CollectionModel<PedidoResumoModel> toCollection (Collection<Pedido> listaPedido) {
        var listaPedidoModel = listaPedido.stream().map(this::toModel).toList();
        CollectionModel<PedidoResumoModel> pedidosCollectionModel = CollectionModel.of(listaPedidoModel);
        if (algaSecurity.podeBuscarPedidos()) {
            pedidosCollectionModel.add(algaLinks.linkToPedidos("pedidos"));
        }

        return pedidosCollectionModel;
    }
}
