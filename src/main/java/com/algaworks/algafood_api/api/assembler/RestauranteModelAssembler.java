package com.algaworks.algafood_api.api.assembler;

import com.algaworks.algafood_api.api.AlgaLinks;
import com.algaworks.algafood_api.api.assembler.mapper.RestauranteMapper;
import com.algaworks.algafood_api.api.model.RestauranteModel;
import com.algaworks.algafood_api.domain.model.Restaurante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class RestauranteModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel> {

    @Autowired
    private RestauranteMapper restauranteMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public RestauranteModelAssembler() {
        super(Restaurante.class, RestauranteModel.class);
    }


    @Override
    public RestauranteModel toModel(Restaurante entity) {
        RestauranteModel restauranteModel = restauranteMapper.toModel(entity);

        restauranteModel.getCozinha().add(algaLinks.
                linkToCozinha(restauranteModel.getCozinha().getId()));
        restauranteModel.getEndereco().getCidade().add(algaLinks.
                linkToCidade(restauranteModel.getEndereco().getCidade().getId()));

        restauranteModel.add(algaLinks.
                linkToRestaurante(restauranteModel.getId()));
        restauranteModel.add(algaLinks.
                linkToRestaurantes("restaurantes"));

        if (entity.podeAbrir()) {
            restauranteModel.add(algaLinks.
                    linkToRestauranteAbrir(restauranteModel.getId() , "abrir"));
        }
        if (entity.podeFechar()) {
            restauranteModel.add(algaLinks.
                    linkToRestauranteFechar(restauranteModel.getId() , "fechar"));
        }
        if (entity.podeAtivar()) {
            restauranteModel.add(algaLinks.
                    linkToRestauranteAtivar(restauranteModel.getId() , "ativar"));
        }
        if (entity.podeInativar()) {
            restauranteModel.add(algaLinks.
                    linkToRestauranteInativar(restauranteModel.getId() , "inativar"));
        }

        restauranteModel.add(algaLinks.
                linkToRestauranteFormasPagamento(restauranteModel.getId(), "formas-pagamento"));
        restauranteModel.add(algaLinks.
                linkToResponsaveisRestaurante(restauranteModel.getId(), "responsaveis"));

        return restauranteModel;
    }

    public CollectionModel<RestauranteModel> toCollection (Collection<Restaurante> listaUsuarios) {
        List<RestauranteModel> listaUsuariosModel = listaUsuarios.stream().map(this::toModel).toList();
        CollectionModel<RestauranteModel> restauranteModels = CollectionModel.of(listaUsuariosModel);

        restauranteModels.add(algaLinks.linkToUsuarios("restaurantes"));

        return restauranteModels;


    }
}
