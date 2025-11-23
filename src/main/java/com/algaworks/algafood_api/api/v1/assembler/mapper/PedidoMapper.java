package com.algaworks.algafood_api.api.v1.assembler.mapper;

import com.algaworks.algafood_api.api.v1.model.ItemPedidoModel;
import com.algaworks.algafood_api.api.v1.model.PedidoModel;
import com.algaworks.algafood_api.domain.model.ItemPedido;
import com.algaworks.algafood_api.domain.model.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    @Bean
    @Mapping(source = "enderecoEntrega.cidade.estado.nome" , target = "enderecoEntrega.cidade.estado")
    @Mapping(source = "itens" , target = "itens" , qualifiedByName = "mapItens")
    PedidoModel toModel(Pedido pedido);

    @Bean
    @Mapping(target = "produtoId" , source = "produto.id")
    @Mapping(target = "produtoNome" , source = "produto.nome")
    ItemPedidoModel itemPedidoToItemPedidoModel (ItemPedido itemPedido);

    @Named("mapItens")
    default List<ItemPedidoModel> mapItens (List<ItemPedido> itens) {
        return itens.stream().map(this::itemPedidoToItemPedidoModel).collect(Collectors.toList());
    }

}

