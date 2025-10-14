package com.algaworks.algafood_api.domain.repository;

import com.algaworks.algafood_api.domain.model.FotoProduto;
import com.algaworks.algafood_api.domain.model.Produto;
import com.algaworks.algafood_api.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto , Long> , ProdutoRepositoryQueries {

//    Sobrescrevendo o findById para receber o Id do restaurante também
    @Query("from Produto where restaurante.id = :restauranteId and id = :produtoId")
    Optional<Produto> findById (@Param("restauranteId") Long restauranteId , @Param("produtoId") Long produtoId);

    List<Produto> findByRestaurante (Restaurante restaurante);

    @Query("from Produto p where p.ativo=true and p.restaurante = :restaurante")
    List<Produto>findAtivosByRestaurante(Restaurante restaurante) ;

    @Query("from FotoProduto f join f.produto p where p.restaurante.id = :restauranteId and " +
            "f.produto.id = :produtoId")
    Optional<FotoProduto> findFotoById (Long restauranteId , Long produtoId) ;
}
