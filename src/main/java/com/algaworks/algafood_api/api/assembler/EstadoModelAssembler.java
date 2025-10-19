package com.algaworks.algafood_api.api.assembler;

import com.algaworks.algafood_api.api.assembler.mapper.EstadoMapper;
import com.algaworks.algafood_api.api.controller.EstadoController;
import com.algaworks.algafood_api.api.model.EstadoModel;
import com.algaworks.algafood_api.domain.model.Estado;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EstadoModelAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoModel> {

    private final EstadoMapper estadoMapper;

    public EstadoModelAssembler(EstadoMapper estadoMapper) {
        super(EstadoController.class, EstadoModel.class);
        this.estadoMapper = estadoMapper;
    }

    @Override
    public EstadoModel toModel(Estado estado) {
        EstadoModel estadoModel = estadoMapper.toModel(estado);

        estadoModel.add(linkTo(methodOn(EstadoController.class).getById(estadoModel.getId()))
                .withSelfRel());
        estadoModel.add(linkTo(methodOn(EstadoController.class).all())
                .withRel(IanaLinkRelations.COLLECTION));

        return estadoModel;
    }

    public CollectionModel<EstadoModel> toCollection (List<Estado> listaEstado) {
        var listaEstadoModel = listaEstado.stream().map(this::toModel).toList();
        CollectionModel<EstadoModel> estadosCollectionModel = CollectionModel.of(listaEstadoModel);
        estadosCollectionModel.add(linkTo(EstadoController.class).withSelfRel());

        return estadosCollectionModel;
    }
}
