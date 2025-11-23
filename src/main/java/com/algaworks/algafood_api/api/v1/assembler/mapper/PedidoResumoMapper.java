package com.algaworks.algafood_api.api.v1.assembler.mapper;

import com.algaworks.algafood_api.api.v1.model.PedidoResumoModel;
import com.algaworks.algafood_api.domain.model.Pedido;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Bean;


@Mapper(componentModel = "spring")
public interface PedidoResumoMapper {

    @Bean
    PedidoResumoModel toModel(Pedido pedido);

}
