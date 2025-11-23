package com.algaworks.algafood_api.api.v2.assembler;

import com.algaworks.algafood_api.api.v2.AlgaLinksV2;
import com.algaworks.algafood_api.api.v2.assembler.mapper.CidadeMapperV2;
import com.algaworks.algafood_api.api.v2.controller.CidadeControllerV2;
import com.algaworks.algafood_api.api.v2.model.CidadeModelV2;
import com.algaworks.algafood_api.domain.model.Cidade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CidadeModelAssemblerV2 extends RepresentationModelAssemblerSupport<Cidade, CidadeModelV2> {

    @Autowired
    private CidadeMapperV2 cidadeMapperV2;

    @Autowired
    private AlgaLinksV2 algaLinks;

    public CidadeModelAssemblerV2() {
        super(CidadeControllerV2.class, CidadeModelV2.class);
    }

    @Override
    public CidadeModelV2 toModel(Cidade cidade) {
        CidadeModelV2 cidadeModel = cidadeMapperV2.toModel(cidade);

        cidadeModel.add(algaLinks.
                linkToCidade(cidadeModel.getIdCidade()));
        cidadeModel.add(algaLinks.
                linkToEstado(cidadeModel.getIdEstado()));
        cidadeModel.add(algaLinks.
                linkToCidades());

        return cidadeModel;
    }

    public CollectionModel<CidadeModelV2> toCollection (List<Cidade> listaCidade) {
        var listaCidadeModel = listaCidade.stream().map(this::toModel).toList();
        CollectionModel<CidadeModelV2> cidadesCollectionModel = CollectionModel.of(listaCidadeModel);
        cidadesCollectionModel.add(algaLinks.
                linkToCidades("cidades"));

        return cidadesCollectionModel;
    }
}
