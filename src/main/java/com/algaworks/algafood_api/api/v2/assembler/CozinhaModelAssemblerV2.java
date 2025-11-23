package com.algaworks.algafood_api.api.v2.assembler;

import com.algaworks.algafood_api.api.v1.AlgaLinks;
import com.algaworks.algafood_api.api.v1.controller.CozinhaController;
import com.algaworks.algafood_api.api.v2.AlgaLinksV2;
import com.algaworks.algafood_api.api.v2.assembler.mapper.CozinhaMapperV2;
import com.algaworks.algafood_api.api.v2.model.CozinhaModelV2;
import com.algaworks.algafood_api.domain.model.Cozinha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CozinhaModelAssemblerV2 extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModelV2> {

    @Autowired
    private CozinhaMapperV2 cozinhaMapper ;

    @Autowired
    private AlgaLinksV2 algaLinks ;

    public CozinhaModelAssemblerV2() {
        super(CozinhaController.class, CozinhaModelV2.class);
    }

    @Override
    public CozinhaModelV2 toModel(Cozinha cozinha) {
        CozinhaModelV2 cozinhaModel = cozinhaMapper.toModel(cozinha);

        cozinhaModel.add(algaLinks.linkToCozinha(cozinhaModel.getIdCozinha()));

        cozinhaModel.add(algaLinks.linkToCozinhas());

        return cozinhaModel;
    }

    public CollectionModel<CozinhaModelV2> toCollection (List<Cozinha> listaCozinha) {
        var listaCozinhaModel = listaCozinha.stream().map(this::toModel).toList();
        CollectionModel<CozinhaModelV2> cozinhasCollectionModel = CollectionModel.of(listaCozinhaModel);

        cozinhasCollectionModel.add(algaLinks.linkToCozinhas());

        return cozinhasCollectionModel;
    }

}
