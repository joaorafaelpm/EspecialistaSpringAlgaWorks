package com.algaworks.algafood_api.api.v1.assembler.mapper;

import com.algaworks.algafood_api.api.v1.model.PedidoResumoModel;
import com.algaworks.algafood_api.domain.model.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Bean;


@Mapper(componentModel = "spring")
public interface PedidoResumoMapper {

    @Mapping(source = "statusPedido", target = "status")
    @Bean
    PedidoResumoModel toModel(Pedido pedido);

}
