package com.algaworks.algafood_api.api.assembler;

import com.algaworks.algafood_api.api.AlgaLinks;
import com.algaworks.algafood_api.api.assembler.mapper.UsuarioMapper;
import com.algaworks.algafood_api.api.controller.*;
import com.algaworks.algafood_api.api.model.UsuarioModel;
import com.algaworks.algafood_api.domain.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class UsuarioModelAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioModel> {

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public UsuarioModelAssembler () {
        super(UsuarioController.class, UsuarioModel.class);
    }

    @Override
    public UsuarioModel toModel(Usuario entity) {
        UsuarioModel usuarioModel = usuarioMapper.toModel(entity);

        usuarioModel.add(algaLinks.linkToUsuario(usuarioModel.getId()));
        usuarioModel.add(algaLinks.linkToUsuarios());
        usuarioModel.add(algaLinks.linkToGruposUsuario(usuarioModel.getId() ,"gruposUsuario" ));

        return usuarioModel;
    }

    public CollectionModel<UsuarioModel> toCollection (Collection<Usuario> listaUsuario) {
        List<UsuarioModel> listaUsuarioModel = listaUsuario.stream().map(this::toModel).toList();
        CollectionModel<UsuarioModel> usuarioModels = CollectionModel.of(listaUsuarioModel);

        usuarioModels.add(algaLinks.linkToUsuarios("usuarios"));

        return usuarioModels;
    }

    public CollectionModel<UsuarioModel> toCollectionRefRestaurante (Long restauranteId , Collection<Usuario> listaUsuario) {
        CollectionModel<UsuarioModel> listaUsuarioModel = toCollection(listaUsuario);

        listaUsuarioModel.forEach(usuarioModel ->
                usuarioModel.add(algaLinks.linkToResponsaveisRestauranteDesassociacao(restauranteId, usuarioModel.getId() , "desassociar")));
        return listaUsuarioModel
                .removeLinks()
                .add(algaLinks.linkToResponsaveisRestaurante(restauranteId))
                .add(algaLinks.linkToResponsaveisRestauranteAssociacao(restauranteId , "associar"));
    }

}
