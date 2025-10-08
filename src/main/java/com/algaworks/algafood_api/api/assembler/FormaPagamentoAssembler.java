package com.algaworks.algafood_api.api.assembler;

import com.algaworks.algafood_api.api.model.FormaPagamentoModel;
import com.algaworks.algafood_api.domain.model.FormaPagamento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Bean;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FormaPagamentoAssembler {

    @Bean
    FormaPagamentoModel formaPagamentoToFormaPagamentoModel(FormaPagamento formaPagamento);

    @Bean
    List<FormaPagamentoModel> toCollection(List<FormaPagamento> listaFormaPagamento);

}
