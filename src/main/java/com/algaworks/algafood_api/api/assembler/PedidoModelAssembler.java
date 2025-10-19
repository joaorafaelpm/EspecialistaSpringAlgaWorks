package com.algaworks.algafood_api.api.assembler;

import com.algaworks.algafood_api.api.assembler.mapper.PedidoMapper;
import com.algaworks.algafood_api.api.controller.*;
import com.algaworks.algafood_api.api.model.PedidoModel;
import com.algaworks.algafood_api.domain.model.FormaPagamento;
import com.algaworks.algafood_api.domain.model.Pedido;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

    private final PedidoMapper pedidoMapper;

    public PedidoModelAssembler(PedidoMapper pedidoMapper) {
        super(PedidoController.class, PedidoModel.class);
        this.pedidoMapper = pedidoMapper;
    }

    @Override
    public PedidoModel toModel(Pedido pedido) {
        PedidoModel pedidoModel = pedidoMapper.toModel(pedido);

        Long restauranteId = pedidoModel.getRestaurante().getId();

        pedidoModel.getRestaurante().add(linkTo(methodOn(RestauranteController.class)
                .getById(restauranteId)).withSelfRel());
        pedidoModel.getCliente().add(linkTo(methodOn(UsuarioController.class)
                .findById(pedidoModel.getCliente().getId())).withSelfRel());
        pedidoModel.getEnderecoEntrega().getCidade().add(linkTo(methodOn(CidadeController.class)
                .getById(pedidoModel.getEnderecoEntrega().getCidade().getId())).withSelfRel());
        pedidoModel.getFormaPagamento().add(linkTo(methodOn(FormaPagamentoController.class)
                .getById(pedidoModel.getFormaPagamento().getId() , null)).withSelfRel());

        pedidoModel.getItens().forEach(itemPedidoModel -> {
            itemPedidoModel.add(linkTo(methodOn(RestauranteProdutosController.class).pegarUnico(restauranteId,itemPedidoModel.getProdutoId())).withSelfRel());
        });

        pedidoModel.add(linkTo(methodOn(PedidoController.class).pegarUm(pedidoModel.getCodigo()))
                .withSelfRel());
        pedidoModel.add(linkTo(PedidoController.class)
                .withRel(IanaLinkRelations.COLLECTION));

        return pedidoModel;
    }

}
