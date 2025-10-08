package com.algaworks.algafood_api.api.assembler.disassambler;

import com.algaworks.algafood_api.api.model.input.FormaPagamentoDTO;
import com.algaworks.algafood_api.domain.model.FormaPagamento;
import org.mapstruct.*;
import org.springframework.context.annotation.Bean;

@Mapper(componentModel = "spring")
public interface FormaPagamentoDisassembler {

    @Bean
    FormaPagamento formaPagamentoDTOToFormaPagamento (FormaPagamentoDTO formaPagamentoDTO) ;

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateFormaPagamentoFromDto(FormaPagamentoDTO dto, @MappingTarget FormaPagamento entity);


}
