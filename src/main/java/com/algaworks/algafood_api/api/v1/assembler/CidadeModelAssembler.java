package com.algaworks.algafood_api.api.v1.assembler;

import com.algaworks.algafood_api.api.v1.AlgaLinks;
import com.algaworks.algafood_api.api.v1.assembler.mapper.CidadeMapper;
import com.algaworks.algafood_api.api.v1.controller.CidadeController;
import com.algaworks.algafood_api.api.v1.model.CidadeModel;
import com.algaworks.algafood_api.domain.model.Cidade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CidadeModelAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeModel> {

    @Autowired
    private CidadeMapper cidadeMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public CidadeModelAssembler () {
        super(CidadeController.class, CidadeModel.class);
    }

    @Override
    public CidadeModel toModel(Cidade cidade) {
        CidadeModel cidadeModel = cidadeMapper.toModel(cidade);

        cidadeModel.add(algaLinks.
                linkToCidade(cidadeModel.getId()));
        cidadeModel.getEstado().add(algaLinks.
                linkToEstado(cidadeModel.getEstado().getId()));
        cidadeModel.add(algaLinks.
                linkToCidades());

        return cidadeModel;
    }

    public CollectionModel<CidadeModel> toCollection (List<Cidade> listaCidade) {
        var listaCidadeModel = listaCidade.stream().map(this::toModel).toList();
        CollectionModel<CidadeModel> cidadesCollectionModel = CollectionModel.of(listaCidadeModel);
        cidadesCollectionModel.add(algaLinks.
                linkToCidades("cidades"));

        return cidadesCollectionModel;
    }
}
