package com.algaworks.algafood_api.api.assembler;

import com.algaworks.algafood_api.api.assembler.mapper.CidadeMapper;
import com.algaworks.algafood_api.api.controller.CidadeController;
import com.algaworks.algafood_api.api.controller.EstadoController;
import com.algaworks.algafood_api.api.model.CidadeModel;
import com.algaworks.algafood_api.domain.model.Cidade;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CidadeModelAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeModel> {

    private final CidadeMapper cidadeMapper;

    public CidadeModelAssembler (CidadeMapper cidadeMapper) {
        super(CidadeController.class, CidadeModel.class);
        this.cidadeMapper = cidadeMapper;
    }

    @Override
    public CidadeModel toModel(Cidade cidade) {
        CidadeModel cidadeModel = cidadeMapper.toModel(cidade);

        cidadeModel.add(linkTo(methodOn(CidadeController.class).getById(cidadeModel.getId()))
                .withSelfRel());
        cidadeModel.getEstado().add(linkTo(methodOn(EstadoController.class)
                .getById(cidadeModel.getEstado().getId())).withSelfRel());
        cidadeModel.add(linkTo(methodOn(CidadeController.class).all())
                .withRel(IanaLinkRelations.COLLECTION));

        return cidadeModel;
    }

    public CollectionModel<CidadeModel> toCollection (List<Cidade> listaCidade) {
        var listaCidadeModel = listaCidade.stream().map(this::toModel).toList();
        CollectionModel<CidadeModel> cidadesCollectionModel = CollectionModel.of(listaCidadeModel);
        cidadesCollectionModel.add(linkTo(CidadeController.class).withSelfRel());

        return cidadesCollectionModel;
    }
}
