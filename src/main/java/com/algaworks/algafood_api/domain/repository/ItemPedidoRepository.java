package com.algaworks.algafood_api.domain.repository;

import com.algaworks.algafood_api.domain.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido , Long> {
}
