package com.algaworks.algafood_api.api.v1.assembler;

import com.algaworks.algafood_api.api.v1.AlgaLinks;
import com.algaworks.algafood_api.api.v1.assembler.mapper.UsuarioMapper;
import com.algaworks.algafood_api.api.v1.model.UsuarioModel;
import com.algaworks.algafood_api.api.v1.controller.UsuarioController;
import com.algaworks.algafood_api.core.security.AlgaSecurity;
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

    @Autowired
    private AlgaSecurity algaSecurity;

    public UsuarioModelAssembler () {
        super(UsuarioController.class, UsuarioModel.class);
    }

    @Override
    public UsuarioModel toModel(Usuario entity) {
        UsuarioModel usuarioModel = usuarioMapper.toModel(entity);

        if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
            usuarioModel.add(algaLinks.linkToUsuario(usuarioModel.getId()));
            usuarioModel.add(algaLinks.linkToUsuarios());
            usuarioModel.add(algaLinks.linkToGruposUsuario(usuarioModel.getId() ,"gruposUsuario" ));
        }

        return usuarioModel;
    }

    public CollectionModel<UsuarioModel> toCollection (Collection<Usuario> listaUsuario) {
        List<UsuarioModel> listaUsuarioModel = listaUsuario.stream().map(this::toModel).toList();
        CollectionModel<UsuarioModel> usuarioModels = CollectionModel.of(listaUsuarioModel);

        if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
            usuarioModels.add(algaLinks.linkToUsuarios("usuarios"));
        }

        return usuarioModels;
    }

    public CollectionModel<UsuarioModel> toCollectionRefRestaurante (Long restauranteId , Collection<Usuario> listaUsuario) {
        CollectionModel<UsuarioModel> listaUsuarioModel = toCollection(listaUsuario);

        if (algaSecurity.podeGerenciarCadastrosRestaurantes()) {
            listaUsuarioModel.forEach(usuarioModel ->
                usuarioModel.add(algaLinks.
                        linkToResponsaveisRestauranteDesassociacao(restauranteId, usuarioModel.getId() , "desassociar")));

            listaUsuarioModel.removeLinks()
                .add(algaLinks.linkToResponsaveisRestaurante(restauranteId))
                .add(algaLinks.linkToResponsaveisRestauranteAssociacao(restauranteId , "associar"));
        }
        return listaUsuarioModel ;
    }

}
