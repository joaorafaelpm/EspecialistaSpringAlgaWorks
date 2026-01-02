package com.algaworks.algafood_api.api.v1.assembler;

import com.algaworks.algafood_api.api.v1.AlgaLinks;
import com.algaworks.algafood_api.api.v1.assembler.mapper.PedidoMapper;
import com.algaworks.algafood_api.api.v1.model.PedidoModel;
import com.algaworks.algafood_api.api.v1.controller.PedidoController;
import com.algaworks.algafood_api.core.security.AlgaSecurity;
import com.algaworks.algafood_api.domain.model.Pedido;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

    @Autowired
    private PedidoMapper pedidoMapper;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

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

        if (algaSecurity.podeGerenciarPedidos(pedido.getCodigo())) {
            if (pedido.podeSerConfirmado()) {
                pedidoModel.add(algaLinks.linkToConfirmarPedido(codigoPedido , "confirmar"));
            }
            if (pedido.podeSerEntregue()) {
                pedidoModel.add(algaLinks.linkToEntregarPedido(codigoPedido , "entregar"));
            }
            if (pedido.podeSerCancelado()) {
                pedidoModel.add(algaLinks.linkToCancelarPedido(codigoPedido , "cancelar"));
            }
        }

        if (algaSecurity.podeBuscarPedidos()) {
            pedidoModel.add(algaLinks.linkToPedidos("pedidos"));
        }
        if (algaSecurity.podeConsultarRestaurantes()) {
            pedidoModel.getRestaurante().add(algaLinks.linkToRestaurante(restauranteId));
            pedidoModel.getItens().forEach(item -> {
                item.add(algaLinks.linkToProduto(
                        pedidoModel.getRestaurante().getId(), item.getProdutoId(), IanaLinkRelations.SELF.value()));
            });
        }
        if (algaSecurity.podeConsultarCidades()) {
            pedidoModel.getEnderecoEntrega().getCidade().add(algaLinks.linkToCidade(cidadeId));
        }
        if (algaSecurity.podeConsultarFormasPagamento()) {
            pedidoModel.getFormaPagamento().add(algaLinks.linkToFormaPagamento(formaPagamentoId));
        }
        if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
            pedidoModel.getCliente().add(algaLinks.linkToUsuario(clienteId));
        }
        return pedidoModel;
    }

}
