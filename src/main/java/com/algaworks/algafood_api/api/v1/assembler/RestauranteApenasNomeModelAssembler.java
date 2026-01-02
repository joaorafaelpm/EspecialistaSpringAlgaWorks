package com.algaworks.algafood_api.api.v1.assembler;

import com.algaworks.algafood_api.api.v1.AlgaLinks;
import com.algaworks.algafood_api.api.v1.assembler.mapper.RestauranteResumoMapper;
import com.algaworks.algafood_api.api.v1.model.RestauranteApenasNomeModel;
import com.algaworks.algafood_api.core.security.AlgaSecurity;
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

    @Autowired
    private AlgaSecurity algaSecurity;

    public RestauranteApenasNomeModelAssembler() {
        super(Restaurante.class, RestauranteApenasNomeModel.class);
    }

    @Override
    public RestauranteApenasNomeModel toModel(Restaurante entity) {
        RestauranteApenasNomeModel restauranteModel = restauranteMapper.toModelResumido(entity);

        if (algaSecurity.podeConsultarRestaurantes() ) {
            restauranteModel.add(algaLinks.
                    linkToRestaurante(restauranteModel.getId()));
            restauranteModel.add(algaLinks.
                    linkToRestaurantes("restaurantes"));
        }


        return restauranteModel;
    }

    public CollectionModel<RestauranteApenasNomeModel> toCollection (Collection<Restaurante> listaUsuarios) {
        List<RestauranteApenasNomeModel> listaUsuariosModel = listaUsuarios.stream().map(this::toModel).toList();
        CollectionModel<RestauranteApenasNomeModel> restauranteModels = CollectionModel.of(listaUsuariosModel);

        if (algaSecurity.podeConsultarRestaurantes() ) {
            restauranteModels.add(algaLinks.linkToRestaurantes("restaurantes"));
        }

        return restauranteModels;


    }
}
