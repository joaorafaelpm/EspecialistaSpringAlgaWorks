package com.algaworks.algafood_api.api.v1.assembler;


import com.algaworks.algafood_api.api.v1.AlgaLinks;
import com.algaworks.algafood_api.api.v1.assembler.mapper.GrupoMapper;
import com.algaworks.algafood_api.api.v1.model.GrupoModel;
import com.algaworks.algafood_api.core.security.AlgaSecurity;
import com.algaworks.algafood_api.domain.model.Grupo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class GrupoAssembler extends RepresentationModelAssemblerSupport<Grupo , GrupoModel> {
    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private GrupoMapper grupoMapper;

    @Autowired
    private AlgaSecurity algaSecurity;

    public GrupoAssembler () {
        super(Grupo.class , GrupoModel.class);
    }


    @Override
    public GrupoModel toModel(Grupo entity) {
        GrupoModel grupoModel = grupoMapper.toModel(entity);

        if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
            grupoModel.add(algaLinks.linkToGrupos());
            grupoModel.add(algaLinks.linkToGrupo(entity.getId()));
            grupoModel.add(algaLinks.linkToGrupoPermissao(entity.getId() , "permissoes"));
        }


        return grupoModel;
    }

    public CollectionModel<GrupoModel> toCollection (Collection<Grupo> listaGrupo) {
        var listaGrupoModel = listaGrupo.stream().map(this::toModel).toList();
        CollectionModel<GrupoModel> gruposCollectionModel = CollectionModel.of(listaGrupoModel);
        if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
            gruposCollectionModel.add(algaLinks.linkToGrupos("grupos"));
        }

        return gruposCollectionModel;
    }



    public CollectionModel<GrupoModel> toCollectionRefUsuario (Long usuarioId , Collection<Grupo> listaGrupo) {
        var listaGrupoModel = listaGrupo.stream().map(this::toModel).toList();
        CollectionModel<GrupoModel> gruposCollectionModel = CollectionModel.of(listaGrupoModel);

        if (algaSecurity.podeEditarUsuariosGruposPermissoes()) {
            gruposCollectionModel.forEach(grupoModel ->
                    grupoModel.add(algaLinks.linkToDesassociacaoGrupoUsuario(usuarioId , grupoModel.getId() , "desassociar" )));
            gruposCollectionModel.add(algaLinks.linkToDesassociacaoGrupoUsuario(usuarioId ,null , "associar" ));
        }
        if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
            gruposCollectionModel.add(algaLinks.linkToGrupos("grupos"));
        }

        return gruposCollectionModel;
    }

}

