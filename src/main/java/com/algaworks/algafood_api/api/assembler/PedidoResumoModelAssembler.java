package com.algaworks.algafood_api.api.assembler;

import com.algaworks.algafood_api.api.assembler.mapper.PedidoMapper;
import com.algaworks.algafood_api.api.assembler.mapper.PedidoResumoMapper;
import com.algaworks.algafood_api.api.controller.*;
import com.algaworks.algafood_api.api.model.PedidoResumoModel;
import com.algaworks.algafood_api.domain.model.Pedido;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PedidoResumoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel> {

    private final PedidoResumoMapper pedidoResumoMapper;

    public PedidoResumoModelAssembler(PedidoResumoMapper pedidoResumoMapper) {
        super(PedidoController.class, PedidoResumoModel.class);
        this.pedidoResumoMapper = pedidoResumoMapper;
    }

    @Override
    public PedidoResumoModel toModel(Pedido pedido) {
        PedidoResumoModel pedidoModel = pedidoResumoMapper.toModel(pedido);

        Long restauranteId = pedidoModel.getRestaurante().getId();

        pedidoModel.getRestaurante().add(linkTo(methodOn(RestauranteController.class)
                .getById(restauranteId)).withSelfRel());
        pedidoModel.getCliente().add(linkTo(methodOn(UsuarioController.class)
                .findById(pedidoModel.getCliente().getId())).withSelfRel());

        pedidoModel.add(linkTo(methodOn(PedidoController.class).pegarUm(pedidoModel.getCodigo()))
                .withSelfRel());
        pedidoModel.add(linkTo(PedidoController.class)
                .withRel(IanaLinkRelations.COLLECTION));

        return pedidoModel;
    }

    public CollectionModel<PedidoResumoModel> toCollection (Collection<Pedido> listaPedido) {
        var listaPedidoModel = listaPedido.stream().map(this::toModel).toList();
        CollectionModel<PedidoResumoModel> pedidosCollectionModel = CollectionModel.of(listaPedidoModel);
        pedidosCollectionModel.add(linkTo(PedidoController.class).withSelfRel());

        return pedidosCollectionModel;
    }
}
