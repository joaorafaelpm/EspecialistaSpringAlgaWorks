package com.algaworks.algafood_api.api.assembler;

import com.algaworks.algafood_api.api.AlgaLinks;
import com.algaworks.algafood_api.api.assembler.mapper.CozinhaMapper;
import com.algaworks.algafood_api.api.controller.CozinhaController;
import com.algaworks.algafood_api.api.model.CozinhaModel;
import com.algaworks.algafood_api.domain.model.Cozinha;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private AlgaLinks algaLinks ;

    public CozinhaModelAssembler(CozinhaMapper cozinhaMapper) {
        super(CozinhaController.class, CozinhaModel.class);
        this.cozinhaMapper = cozinhaMapper;
    }

    @Override
    public CozinhaModel toModel(Cozinha cozinha) {
        CozinhaModel cozinhaModel = cozinhaMapper.toModel(cozinha);

        cozinhaModel.add(algaLinks.linkToCozinha(cozinhaModel.getId()));

        cozinhaModel.add(algaLinks.linkToCozinhas());

        return cozinhaModel;
    }

    public CollectionModel<CozinhaModel> toCollection (List<Cozinha> listaCozinha) {
        var listaCozinhaModel = listaCozinha.stream().map(this::toModel).toList();
        CollectionModel<CozinhaModel> cozinhasCollectionModel = CollectionModel.of(listaCozinhaModel);

        cozinhasCollectionModel.add(algaLinks.linkToCozinhas());

        return cozinhasCollectionModel;
    }

}
