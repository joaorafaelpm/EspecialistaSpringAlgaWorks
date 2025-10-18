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

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CidadeModelAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeModel> {

    private final CidadeMapper cidadeAssembler;

    public CidadeModelAssembler (CidadeMapper cidadeAssembler) {
        super(CidadeController.class, CidadeModel.class);
        this.cidadeAssembler = cidadeAssembler;
    }

    @Override
    public CidadeModel toModel(Cidade cidade) {
        CidadeModel cidadeModel = cidadeAssembler.toModel(cidade);

        cidadeModel.add(linkTo(methodOn(CidadeController.class).getById(cidadeModel.getId()))
                .withSelfRel());
        cidadeModel.getEstado().add(linkTo(methodOn(EstadoController.class)
                .getById(cidadeModel.getEstado().getId())).withSelfRel());
        cidadeModel.add(linkTo(methodOn(CidadeController.class).all())
                .withRel(IanaLinkRelations.COLLECTION));

        return cidadeModel;
    }

    public CollectionModel<CidadeModel> toCollection (List<Cidade> listaCidade) {
//        Desta forma não podemos ter links únicos nas entidades da lista já que usamos o toCollection do CidadeMapper, então eu sobrescrevo na mão, para manter os padrões do mapper sem precisar alterar a interface enquanto eu sobrescrevo o padrão com o toModel da classe atual
//        List<CidadeModel> collection = cidadeAssembler.toCollection(listaCidade);
//        CollectionModel<CidadeModel> cidadesCollectionModel = CollectionModel.of(collection);
//        cidadesCollectionModel.add(linkTo(CidadeController.class).withSelfRel());

        var listaCidadeModel = listaCidade.stream().map(this::toModel).toList();
        CollectionModel<CidadeModel> cidadesCollectionModel = CollectionModel.of(listaCidadeModel);
        cidadesCollectionModel.add(linkTo(CidadeController.class).withSelfRel());

        return cidadesCollectionModel;
    }
}
