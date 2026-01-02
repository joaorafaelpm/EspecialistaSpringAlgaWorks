package com.algaworks.algafood_api.api.v1.assembler;

import com.algaworks.algafood_api.api.v1.AlgaLinks;
import com.algaworks.algafood_api.api.v1.assembler.mapper.FormaPagamentoMapper;
import com.algaworks.algafood_api.api.v1.model.FormaPagamentoModel;
import com.algaworks.algafood_api.core.security.AlgaSecurity;
import com.algaworks.algafood_api.domain.model.FormaPagamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class FormaPagamentoAssembler extends RepresentationModelAssemblerSupport<FormaPagamento , FormaPagamentoModel> {

    @Autowired
    private FormaPagamentoMapper formaPagamentoMapper ;
    @Autowired
    private AlgaLinks algaLinks ;

    @Autowired
    private AlgaSecurity algaSecurity;

    public FormaPagamentoAssembler () {
        super(FormaPagamento.class , FormaPagamentoModel.class);
    }


    @Override
    public FormaPagamentoModel toModel(FormaPagamento entity) {
        FormaPagamentoModel formaPagamentoModel = formaPagamentoMapper.toModel(entity);

        if (algaSecurity.podeConsultarFormasPagamento()) {
            formaPagamentoModel.add(algaLinks.linkToFormaPagamento(formaPagamentoModel.getId()));
            formaPagamentoModel.add(algaLinks.linkToFormasPagamento("formasPagamento"));
        }


        return formaPagamentoModel;
    }

    public CollectionModel<FormaPagamentoModel> toCollection (Collection<FormaPagamento> listaFormaPagamento) {
        List<FormaPagamentoModel> list = listaFormaPagamento.stream().map(this::toModel).toList();
        CollectionModel<FormaPagamentoModel> formasPagamentoModels = CollectionModel.of(list);
        if (algaSecurity.podeConsultarFormasPagamento()) {
            formasPagamentoModels.add(algaLinks.linkToFormasPagamento("formasPagamento"));
        }
        return formasPagamentoModels;
    }



//    Eu crio essa função extra para reduzir o número de funções do controlador, aqui eu faço o mesmo collection, porém modelado para a representação de formas de pagamento do restaurante
    public CollectionModel<FormaPagamentoModel> toCollectionRefRestaurante (Long restauranteId , Collection<FormaPagamento> listaFormaPagamento) {
        CollectionModel<FormaPagamentoModel> listaFormaPagentoModel = toCollection(listaFormaPagamento);

        if (algaSecurity.podeGerenciarFuncionamentoRestaurantes(restauranteId)) {
            listaFormaPagentoModel.forEach(
                    formaPagamentoModel ->
                            formaPagamentoModel.add(algaLinks.
                                    linkToRestauranteFormaPagamentoDesassociacao(
                                            restauranteId , formaPagamentoModel.getId() , "desassociar"))
            );
        }
        listaFormaPagentoModel
                .removeLinks();
        if (algaSecurity.podeConsultarRestaurantes()) {
            listaFormaPagentoModel
                    .add(algaLinks.linkToRestauranteFormasPagamento(restauranteId));
        }
        if (algaSecurity.podeGerenciarCadastrosRestaurantes()) {
            listaFormaPagentoModel
                    .add(algaLinks.linkToRestauranteFormaPagamentoAssociacao(restauranteId , "associacao"));
        }

        return listaFormaPagentoModel;
    }
}
