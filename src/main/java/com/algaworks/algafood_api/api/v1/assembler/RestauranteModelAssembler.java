package com.algaworks.algafood_api.api.v1.assembler;

import com.algaworks.algafood_api.api.v1.AlgaLinks;
import com.algaworks.algafood_api.api.v1.assembler.mapper.RestauranteMapper;
import com.algaworks.algafood_api.api.v1.model.RestauranteModel;
import com.algaworks.algafood_api.core.security.AlgaSecurity;
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

    @Autowired
    private AlgaSecurity algaSecurity;

    public RestauranteModelAssembler() {
        super(Restaurante.class, RestauranteModel.class);
    }


    @Override
    public RestauranteModel toModel(Restaurante entity) {
        RestauranteModel restauranteModel = restauranteMapper.toModel(entity);


        if (algaSecurity.podeConsultarCozinhas()) {
            restauranteModel.getCozinha().add(algaLinks.
                    linkToCozinha(restauranteModel.getCozinha().getId()));
        }
        if (algaSecurity.podeConsultarCidades()) {
            if (restauranteModel.getEndereco() != null) {
                restauranteModel.getEndereco().getCidade().add(algaLinks.
                        linkToCidade(restauranteModel.getEndereco().getCidade().getId()));
            }
        }

        if (algaSecurity.podeConsultarRestaurantes()) {
            restauranteModel.add(algaLinks.
                    linkToRestaurante(restauranteModel.getId()));
            restauranteModel.add(algaLinks.
                    linkToRestaurantes("restaurantes"));
            restauranteModel.add(algaLinks.
                    linkToProdutosRestaurante(restauranteModel.getId() , "produtos"));
        }

        if(algaSecurity.podeGerenciarFuncionamentoRestaurantes(restauranteModel.getId())) {
            if (entity.podeAbrir()) {
                restauranteModel.add(algaLinks.
                        linkToRestauranteAbertura(restauranteModel.getId() , "abrir"));
            }
            if (entity.podeFechar()) {
                restauranteModel.add(algaLinks.
                        linkToRestauranteFechamento(restauranteModel.getId() , "fechar"));
            }
        }

        if (algaSecurity.podeGerenciarCadastrosRestaurantes()) {
            if (entity.podeAtivar()) {
                restauranteModel.add(algaLinks.
                        linkToRestauranteAtivacao(restauranteModel.getId() , "ativar"));
            }
            if (entity.podeInativar()) {
                restauranteModel.add(algaLinks.
                        linkToRestauranteInativacao(restauranteModel.getId() , "inativar"));
            }

            restauranteModel.add(algaLinks.
                    linkToResponsaveisRestaurante(restauranteModel.getId(), "responsaveis"));
            restauranteModel.add(algaLinks.
                    linkToRestauranteFormasPagamento(restauranteModel.getId(), "formas-pagamento"));
        }
        return restauranteModel;
    }

    public CollectionModel<RestauranteModel> toCollection (Collection<Restaurante> listaUsuarios) {
        List<RestauranteModel> listaUsuariosModel = listaUsuarios.stream().map(this::toModel).toList();
        CollectionModel<RestauranteModel> restauranteModels = CollectionModel.of(listaUsuariosModel);

        if (algaSecurity.podeConsultarRestaurantes()) {
            restauranteModels.add(algaLinks.linkToUsuarios("restaurantes"));
        }

        return restauranteModels;


    }
}
