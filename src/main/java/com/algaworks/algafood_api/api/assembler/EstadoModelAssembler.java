package com.algaworks.algafood_api.api.assembler;

import com.algaworks.algafood_api.api.AlgaLinks;
import com.algaworks.algafood_api.api.assembler.mapper.EstadoMapper;
import com.algaworks.algafood_api.api.controller.EstadoController;
import com.algaworks.algafood_api.api.model.EstadoModel;
import com.algaworks.algafood_api.domain.model.Estado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EstadoModelAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoModel> {

    @Autowired
    private EstadoMapper estadoMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public EstadoModelAssembler() {
        super(EstadoController.class, EstadoModel.class);
    }

    @Override
    public EstadoModel toModel(Estado estado) {
        EstadoModel estadoModel = estadoMapper.toModel(estado);

        estadoModel.add(algaLinks.linkToEstado(estadoModel.getId()));
        estadoModel.add(algaLinks.linkToEstados());

        return estadoModel;
    }

    public CollectionModel<EstadoModel> toCollection (List<Estado> listaEstado) {
        var listaEstadoModel = listaEstado.stream().map(this::toModel).toList();
        CollectionModel<EstadoModel> estadosCollectionModel = CollectionModel.of(listaEstadoModel);
        estadosCollectionModel.add(algaLinks.linkToEstados());

        return estadosCollectionModel;
    }
}
