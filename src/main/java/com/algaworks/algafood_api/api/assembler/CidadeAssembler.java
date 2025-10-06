package com.algaworks.algafood_api.api.assembler;

import com.algaworks.algafood_api.api.model.CidadeModel;
import com.algaworks.algafood_api.domain.model.Cidade;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Bean;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CidadeAssembler {

    @Bean
    CidadeModel cidadeToCidadeModel(Cidade cidade);

    @Bean
    List<CidadeModel> toCollection(List<Cidade> listaCidade);

}
