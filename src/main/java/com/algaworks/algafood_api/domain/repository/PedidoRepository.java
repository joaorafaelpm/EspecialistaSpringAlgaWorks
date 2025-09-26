package com.algaworks.algafood_api.domain.repository;

import com.algaworks.algafood_api.domain.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido , Long> {
}
