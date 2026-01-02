package com.algaworks.algafood_api.api.v1.assembler;

import com.algaworks.algafood_api.api.v1.AlgaLinks;
import com.algaworks.algafood_api.api.v1.assembler.mapper.PermissaoMapper;
import com.algaworks.algafood_api.api.v1.model.PermissaoModel;
import com.algaworks.algafood_api.core.security.AlgaSecurity;
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

    @Autowired
    private AlgaSecurity algaSecurity;

    public PermissaoAssembler () {
        super(Permissao.class , PermissaoModel.class);
    }


    @Override
    public PermissaoModel toModel(Permissao entity) {
        PermissaoModel permissaoModel = permissaoMapper.toModel(entity);
        if(algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
            permissaoModel.add(algaLinks.linkToPermissoes());
        }
        return permissaoModel;
    }

    public CollectionModel<PermissaoModel> toCollection (Collection<Permissao> listaPermissao) {
        var listaPermissaoModel = listaPermissao.stream().map(this::toModel).toList();
        CollectionModel<PermissaoModel> permissoesCollectionModel = CollectionModel.of(listaPermissaoModel);

        if(algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
            permissoesCollectionModel.add(algaLinks.linkToPermissoes("permissoes"));
        }

        return permissoesCollectionModel;
    }
    public CollectionModel<PermissaoModel> toCollectionRefGrupo (Long grupoId , Collection<Permissao> listaPermissao) {
        var listaPermissaoModel = listaPermissao.stream().map(this::toModel).toList();
        CollectionModel<PermissaoModel> permissoesCollectionModel = CollectionModel.of(listaPermissaoModel);

        if(algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
            permissoesCollectionModel.add(algaLinks.linkToPermissoes("permissoes"));
        }

        if (algaSecurity.podeEditarUsuariosGruposPermissoes()) {
            permissoesCollectionModel.forEach(permissaoModel ->
                    permissaoModel.add(algaLinks.
                            linkToDesassociacaoGrupoPermissao(grupoId ,permissaoModel.getId(), "desassociar")));
            permissoesCollectionModel.add(algaLinks.linkToAssociacaoGrupoPermissao(grupoId , null , "associar"));
        }

        return permissoesCollectionModel;
    }

}

