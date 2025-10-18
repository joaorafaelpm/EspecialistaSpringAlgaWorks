package com.algaworks.algafood_api.api.assembler.disassambler;

import com.algaworks.algafood_api.api.model.DTO.ItemPedidoDTO;
import com.algaworks.algafood_api.api.model.DTO.PedidoDTO;
import com.algaworks.algafood_api.domain.model.ItemPedido;
import com.algaworks.algafood_api.domain.model.Pedido;
import org.mapstruct.*;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PedidoDisassembler {

    @Mapping(source = "restauranteId", target = "restaurante")
    @Mapping(source = "clienteId", target = "cliente")
    @Mapping(source = "formaPagamentoId", target = "formaPagamento")
    @Mapping(source = "itens", target = "itens", qualifiedByName = "mapItens")
    Pedido pedidoDTOToPedido(PedidoDTO pedidoDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updatePedidoFromDto(PedidoDTO dto, @MappingTarget Pedido entity);

    // Mapeia de DTO → Entidade
    @Mapping(target = "produto.id", source = "produtoId")
    ItemPedido itemPedidoDTOToItemPedido(ItemPedidoDTO itemPedidoDTO);

    // Mapeia lista de DTOs → lista de entidades
    @Named("mapItens")
    default List<ItemPedido> mapItens(List<ItemPedidoDTO> itensDTO) {
        return itensDTO.stream()
                .map(this::itemPedidoDTOToItemPedido)
                .collect(Collectors.toList());
    }
}

