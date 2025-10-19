package com.algaworks.algafood_api.api.assembler;

import com.algaworks.algafood_api.api.assembler.mapper.CozinhaMapper;
import com.algaworks.algafood_api.api.controller.CozinhaController;
import com.algaworks.algafood_api.api.model.CozinhaModel;
import com.algaworks.algafood_api.domain.model.Cozinha;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CozinhaModelAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModel> {

    private final CozinhaMapper cozinhaMapper ;

    public CozinhaModelAssembler(CozinhaMapper cozinhaMapper) {
        super(CozinhaController.class, CozinhaModel.class);
        this.cozinhaMapper = cozinhaMapper;
    }

    @Override
    public CozinhaModel toModel(Cozinha cozinha) {
        CozinhaModel cozinhaModel = cozinhaMapper.toModel(cozinha);

        cozinhaModel.add(linkTo(methodOn(CozinhaController.class).getById(cozinhaModel.getId()))
                .withSelfRel());
        cozinhaModel.add(linkTo(CozinhaController.class)
                .withRel(IanaLinkRelations.COLLECTION));

        return cozinhaModel;
    }

    public CollectionModel<CozinhaModel> toCollection (List<Cozinha> listaCozinha) {
        var listaCozinhaModel = listaCozinha.stream().map(this::toModel).toList();
        CollectionModel<CozinhaModel> cozinhasCollectionModel = CollectionModel.of(listaCozinhaModel);
        cozinhasCollectionModel.add(linkTo(CozinhaController.class).withSelfRel());

        return cozinhasCollectionModel;
    }

}
