package com.algaworks.algafood_api.api.assembler;

import com.algaworks.algafood_api.api.model.FormaPagamentoModel;
import com.algaworks.algafood_api.domain.model.FormaPagamento;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface FormaPagamentoAssembler {

    @Bean
    FormaPagamentoModel formaPagamentoToFormaPagamentoModel(FormaPagamento formaPagamento);

    @Bean
//    Pq o Set tbm é collection
    List<FormaPagamentoModel> toCollection(Collection<FormaPagamento> listaFormaPagamento);

}
