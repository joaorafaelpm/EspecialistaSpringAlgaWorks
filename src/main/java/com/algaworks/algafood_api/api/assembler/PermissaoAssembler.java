package com.algaworks.algafood_api.api.assembler;

import com.algaworks.algafood_api.api.AlgaLinks;
import com.algaworks.algafood_api.api.assembler.mapper.PermissaoMapper;
import com.algaworks.algafood_api.api.model.PermissaoModel;
import com.algaworks.algafood_api.domain.model.Permissao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class PermissaoAssembler extends RepresentationModelAssemblerSupport<Permissao , PermissaoModel> {
    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private PermissaoMapper permissaoMapper;

    public PermissaoAssembler () {
        super(Permissao.class , PermissaoModel.class);
    }


    @Override
    public PermissaoModel toModel(Permissao entity) {
        PermissaoModel permissaoModel = permissaoMapper.toModel(entity);
        permissaoModel.add(algaLinks.linkToPermissoes());

        return permissaoModel;
    }

    public CollectionModel<PermissaoModel> toCollection (Collection<Permissao> listaPermissao) {
        var listaPermissaoModel = listaPermissao.stream().map(this::toModel).toList();
        CollectionModel<PermissaoModel> permissoesCollectionModel = CollectionModel.of(listaPermissaoModel);
        permissoesCollectionModel.add(algaLinks.linkToPermissoes("permissoes"));

        return permissoesCollectionModel;
    }
    public CollectionModel<PermissaoModel> toCollectionRefGrupo (Long grupoId , Collection<Permissao> listaPermissao) {
        var listaPermissaoModel = listaPermissao.stream().map(this::toModel).toList();
        CollectionModel<PermissaoModel> permissoesCollectionModel = CollectionModel.of(listaPermissaoModel);

        permissoesCollectionModel.forEach(permissaoModel ->
                permissaoModel.add(algaLinks.
                        linkToDesassociacaoGrupoPermissao(grupoId ,permissaoModel.getId(), "desassociar")));
        permissoesCollectionModel.add(algaLinks.linkToPermissoes("permissoes"));
        permissoesCollectionModel.add(algaLinks.linkToAssociacaoGrupoPermissao(grupoId , null , "associar"));

        return permissoesCollectionModel;
    }

}

