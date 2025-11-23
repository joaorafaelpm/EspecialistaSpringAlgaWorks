package com.algaworks.algafood_api.api.v2.assembler.mapper;

import com.algaworks.algafood_api.api.v2.model.CidadeModelV2;
import com.algaworks.algafood_api.domain.model.Cidade;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Bean;

@Mapper(componentModel = "spring")
public interface CidadeMapperV2 {

    @Bean
    @Mapping(source = "id", target = "idCidade" )
    @Mapping(source = "nome", target = "nomeCidade" )
    @Mapping(source = "estado.id", target = "idEstado" )
    @Mapping(source = "estado.nome", target = "nomeEstado" )
    CidadeModelV2 toModel(Cidade cidade);


}
