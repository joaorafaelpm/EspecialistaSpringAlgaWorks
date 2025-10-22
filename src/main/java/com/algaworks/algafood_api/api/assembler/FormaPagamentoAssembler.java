package com.algaworks.algafood_api.api.assembler;

import com.algaworks.algafood_api.api.AlgaLinks;
import com.algaworks.algafood_api.api.assembler.mapper.FormaPagamentoMapper;
import com.algaworks.algafood_api.api.model.FormaPagamentoModel;
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

    public FormaPagamentoAssembler () {
        super(FormaPagamento.class , FormaPagamentoModel.class);
    }


    @Override
    public FormaPagamentoModel toModel(FormaPagamento entity) {
        FormaPagamentoModel formaPagamentoModel = formaPagamentoMapper.toModel(entity);

        formaPagamentoModel.add(algaLinks.linkToFormaPagamento(formaPagamentoModel.getId()));
        formaPagamentoModel.add(algaLinks.linkToFormasPagamento("formasPagamento"));

        return formaPagamentoModel;
    }

    public CollectionModel<FormaPagamentoModel> toCollection (Collection<FormaPagamento> listaFormaPagamento) {
        List<FormaPagamentoModel> list = listaFormaPagamento.stream().map(this::toModel).toList();
        CollectionModel<FormaPagamentoModel> formasPagamentoModels = CollectionModel.of(list);

        formasPagamentoModels.add(algaLinks.linkToFormasPagamento("formasPagamento"));
        return formasPagamentoModels;
    }

//    Eu crio essa função extra para reduzir o número de funções do controlador, aqui eu faço o mesmo collection, porém modelado para a representação de formas de pagamento do restaurante
    public CollectionModel<FormaPagamentoModel> toCollectionRefRestaurante (Long restauranteId , Collection<FormaPagamento> listaFormaPagamento) {
        CollectionModel<FormaPagamentoModel> listaFormaPagentoModel = toCollection(listaFormaPagamento);
        listaFormaPagentoModel.forEach(
                formaPagamentoModel ->
                        formaPagamentoModel.add(algaLinks.
                                linkToRestauranteFormaPagamentoDesassociacao(
                                        restauranteId , formaPagamentoModel.getId() , "desassociar"))
        );

        return listaFormaPagentoModel
//                Removo os links antigos do collection ("/formas-pagamento") para passar o novo ("/restaurante/{restauranteId}/formas-pagamento")
                .removeLinks()
                .add(algaLinks.linkToRestauranteFormasPagamento(restauranteId))
                .add(algaLinks.linkToRestauranteFormaPagamentoAssociacao(restauranteId , "associacao"));

    }
}
