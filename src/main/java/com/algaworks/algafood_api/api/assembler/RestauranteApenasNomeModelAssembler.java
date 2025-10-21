package com.algaworks.algafood_api.api.assembler;

import com.algaworks.algafood_api.api.AlgaLinks;
import com.algaworks.algafood_api.api.assembler.mapper.RestauranteResumoMapper;
import com.algaworks.algafood_api.api.model.RestauranteApenasNomeModel;
import com.algaworks.algafood_api.domain.model.Restaurante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class RestauranteApenasNomeModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteApenasNomeModel> {

    @Autowired
    private RestauranteResumoMapper restauranteMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public RestauranteApenasNomeModelAssembler() {
        super(Restaurante.class, RestauranteApenasNomeModel.class);
    }

    @Override
    public RestauranteApenasNomeModel toModel(Restaurante entity) {
        RestauranteApenasNomeModel restauranteModel = restauranteMapper.toModelResumido(entity);

        restauranteModel.add(algaLinks.
                linkToRestaurante(restauranteModel.getId()));
        restauranteModel.add(algaLinks.
                linkToRestaurantes("restaurantes"));

        return restauranteModel;
    }

    public CollectionModel<RestauranteApenasNomeModel> toCollection (Collection<Restaurante> listaUsuarios) {
        List<RestauranteApenasNomeModel> listaUsuariosModel = listaUsuarios.stream().map(this::toModel).toList();
        CollectionModel<RestauranteApenasNomeModel> restauranteModels = CollectionModel.of(listaUsuariosModel);

        restauranteModels.add(algaLinks.linkToRestaurantes("restaurantes"));

        return restauranteModels;


    }
}
