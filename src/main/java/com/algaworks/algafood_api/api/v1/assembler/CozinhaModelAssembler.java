package com.algaworks.algafood_api.api.v1.assembler;

import com.algaworks.algafood_api.api.v1.AlgaLinks;
import com.algaworks.algafood_api.api.v1.assembler.mapper.CozinhaMapper;
import com.algaworks.algafood_api.api.v1.controller.CozinhaController;
import com.algaworks.algafood_api.api.v1.model.CozinhaModel;
import com.algaworks.algafood_api.core.security.AlgaSecurity;
import com.algaworks.algafood_api.domain.model.Cozinha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CozinhaModelAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModel> {

    @Autowired
    private CozinhaMapper cozinhaMapper ;

    @Autowired
    private AlgaLinks algaLinks ;

    @Autowired
    private AlgaSecurity algaSecurity;

    public CozinhaModelAssembler() {
        super(CozinhaController.class, CozinhaModel.class);
    }

    @Override
    public CozinhaModel toModel(Cozinha cozinha) {
        CozinhaModel cozinhaModel = cozinhaMapper.toModel(cozinha);
        if (algaSecurity.podeConsultarCozinhas()) {
            cozinhaModel.add(algaLinks.linkToCozinha(cozinhaModel.getId()));

            cozinhaModel.add(algaLinks.linkToCozinhas());
        }


        return cozinhaModel;
    }

    public CollectionModel<CozinhaModel> toCollection (List<Cozinha> listaCozinha) {
        var listaCozinhaModel = listaCozinha.stream().map(this::toModel).toList();
        CollectionModel<CozinhaModel> cozinhasCollectionModel = CollectionModel.of(listaCozinhaModel);

        if (algaSecurity.podeConsultarCozinhas()) {
            cozinhasCollectionModel.add(algaLinks.linkToCozinhas());
        }

        return cozinhasCollectionModel;
    }

}
