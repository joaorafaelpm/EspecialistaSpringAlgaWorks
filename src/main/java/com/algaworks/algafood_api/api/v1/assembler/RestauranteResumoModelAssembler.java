package com.algaworks.algafood_api.api.v1.assembler;

import com.algaworks.algafood_api.api.v1.AlgaLinks;
import com.algaworks.algafood_api.api.v1.assembler.mapper.RestauranteResumoMapper;
import com.algaworks.algafood_api.api.v1.model.RestauranteResumoModel;
import com.algaworks.algafood_api.core.security.AlgaSecurity;
import com.algaworks.algafood_api.domain.model.Restaurante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class RestauranteResumoModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteResumoModel> {

    @Autowired
    private RestauranteResumoMapper restauranteMapper;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    public RestauranteResumoModelAssembler() {
        super(Restaurante.class, RestauranteResumoModel.class);
    }


    @Override
    public RestauranteResumoModel toModel(Restaurante entity) {
        RestauranteResumoModel restauranteResumoModel = restauranteMapper.toModel(entity);


        if (algaSecurity.podeConsultarCozinhas()) {
            restauranteResumoModel.getCozinha().add(algaLinks.
                    linkToCozinha(restauranteResumoModel.getCozinha().getId()));
        }
        if (algaSecurity.podeConsultarRestaurantes()) {
            restauranteResumoModel.add(algaLinks.
                    linkToRestaurante(restauranteResumoModel.getId()));
            restauranteResumoModel.add(algaLinks.
                    linkToRestaurantes("restaurantes"));
        }


        return restauranteResumoModel;
    }

    public CollectionModel<RestauranteResumoModel> toCollection (Collection<Restaurante> listaUsuarios) {
        List<RestauranteResumoModel> listaUsuariosModel = listaUsuarios.stream().map(this::toModel).toList();
        CollectionModel<RestauranteResumoModel> restauranteModels = CollectionModel.of(listaUsuariosModel);

        if (algaSecurity.podeConsultarRestaurantes()) {
            restauranteModels.add(algaLinks.linkToRestaurantes("restaurantes"));
        }

        return restauranteModels;


    }
}
