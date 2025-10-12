package com.algaworks.algafood_api.api.assembler;

import com.algaworks.algafood_api.api.model.CozinhaModel;
import com.algaworks.algafood_api.api.model.ItemPedidoModel;
import com.algaworks.algafood_api.api.model.PedidoModel;
import com.algaworks.algafood_api.api.model.PedidoResumoModel;
import com.algaworks.algafood_api.domain.model.Cozinha;
import com.algaworks.algafood_api.domain.model.ItemPedido;
import com.algaworks.algafood_api.domain.model.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PedidoAssembler {

    @Bean
    @Mapping(source = "enderecoEntrega.cidade.estado.nome" , target = "enderecoEntrega.cidade.estado")
    @Mapping(source = "itens" , target = "itens" , qualifiedByName = "mapItens")
    PedidoModel pedidoToPedidoModel(Pedido pedido);

    @Bean
    @Mapping(source = "cliente.nome" , target = "nomeCliente")
    PedidoResumoModel pedidoToPedidoResumoModel(Pedido pedido);

    @Bean
    List<PedidoResumoModel> toCollection(Collection<Pedido> listaPedido);

    default Page<PedidoResumoModel> toPageable (Page<Pedido> paginaPedido) {
        return paginaPedido.map(this::pedidoToPedidoResumoModel);
    }

//    <Mapeando manualmente pq o mapstruct é mágico mas é um saco>
    @Bean
    @Mapping(target = "produtoId" , source = "produto.id")
    @Mapping(target = "produtoNome" , source = "produto.nome")
    ItemPedidoModel itemPedidoToItemPedidoModel (ItemPedido itemPedido);

    @Named("mapItens")
    default List<ItemPedidoModel> mapItens (List<ItemPedido> itens) {
        return itens.stream().map(this::itemPedidoToItemPedidoModel).collect(Collectors.toList());
    }
//    </Mapeando manualmente pq o mapstruct é mágico mas é um saco>

}

