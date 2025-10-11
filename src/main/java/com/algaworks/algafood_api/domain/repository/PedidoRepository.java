package com.algaworks.algafood_api.domain.repository;

import com.algaworks.algafood_api.domain.model.Pedido;
import com.algaworks.algafood_api.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido , Long> {

    @Query("""
        SELECT p FROM Pedido p
        JOIN FETCH p.restaurante r
        JOIN FETCH r.cozinha
        JOIN FETCH p.enderecoEntrega.cidade c
        JOIN FETCH c.estado
        WHERE p.codigo = :codigo
    """)
    Optional<Pedido> findByIdMapperResolved(String codigo);
    List<Pedido> findAll ();

    Optional<Pedido> findByCodigo (String codigo);

}
