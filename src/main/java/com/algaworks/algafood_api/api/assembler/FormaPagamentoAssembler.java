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
        formaPagamentoModel.add(algaLinks.linkToFormasPagamento());

        return formaPagamentoModel;
    }

    public CollectionModel<FormaPagamentoModel> toCollection (Collection<FormaPagamento> listaFormaPagamento) {
        List<FormaPagamentoModel> list = listaFormaPagamento.stream().map(this::toModel).toList();
        CollectionModel<FormaPagamentoModel> formasPagamentoModels = CollectionModel.of(list);

        formasPagamentoModels.add(algaLinks.linkToFormasPagamento("formas-pagamento"));
        return formasPagamentoModels;
    }
}
