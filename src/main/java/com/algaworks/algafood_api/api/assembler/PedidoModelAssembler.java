package com.algaworks.algafood_api.api.assembler;

import com.algaworks.algafood_api.api.AlgaLinks;
import com.algaworks.algafood_api.api.assembler.mapper.PedidoMapper;
import com.algaworks.algafood_api.api.controller.*;
import com.algaworks.algafood_api.api.model.PedidoModel;
import com.algaworks.algafood_api.domain.model.Pedido;
import com.algaworks.algafood_api.domain.model.enuns.StatusPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

    @Autowired
    private PedidoMapper pedidoMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public PedidoModelAssembler() {
        super(PedidoController.class, PedidoModel.class);
    }

    @Override
    public PedidoModel toModel(Pedido pedido) {
        PedidoModel pedidoModel = pedidoMapper.toModel(pedido);

        String codigoPedido = pedidoModel.getCodigo();
        Long restauranteId = pedidoModel.getRestaurante().getId();
        Long clienteId = pedidoModel.getCliente().getId();
        Long cidadeId = pedidoModel.getEnderecoEntrega().getCidade().getId();
        Long formaPagamentoId = pedidoModel.getFormaPagamento().getId();

        if (pedido.podeSerConfirmado()) {
            pedidoModel.add(algaLinks.linkToConfirmarPedido(codigoPedido , "confirmar"));
        }
        if (pedido.podeSerEntregue()) {
            pedidoModel.add(algaLinks.linkToEntregarPedido(codigoPedido , "entregar"));
        }
        if (pedido.podeSerCancelado()) {
            pedidoModel.add(algaLinks.linkToCancelarPedido(codigoPedido , "cancelar"));
        }


        pedidoModel.add(algaLinks.linkToPedidos("pedidos"));
        pedidoModel.getRestaurante().add(algaLinks.linkToRestaurante(restauranteId));
        pedidoModel.getEnderecoEntrega().getCidade().add(algaLinks.linkToCidade(cidadeId));
        pedidoModel.getFormaPagamento().add(algaLinks.linkToFormaPagamento(formaPagamentoId));
        pedidoModel.getCliente().add(algaLinks.linkToUsuario(clienteId));

        pedidoModel.getItens().forEach(item -> {
            item.add(algaLinks.linkToProduto(
                    pedidoModel.getRestaurante().getId(), item.getProdutoId(), IanaLinkRelations.SELF.value()));
        });

        return pedidoModel;
    }

}
