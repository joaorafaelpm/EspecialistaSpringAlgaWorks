package com.algaworks.algafood_api.api.v1.assembler.mapper;

import com.algaworks.algafood_api.api.v1.model.FormaPagamentoModel;
import com.algaworks.algafood_api.domain.model.FormaPagamento;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface FormaPagamentoMapper {

    @Bean
    FormaPagamentoModel toModel(FormaPagamento formaPagamento);

    @Bean
//    Pq o Set tbm é collection
    List<FormaPagamentoModel> toCollection(Collection<FormaPagamento> listaFormaPagamento);

}
