package com.algaworks.algafood_api.domain.repository;

import com.algaworks.algafood_api.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestauranteRepository
        extends CustomJPARepository<Restaurante , Long> , RestauranteRepositoryQueries ,
        JpaSpecificationExecutor<Restaurante> {

    List<Restaurante> findByTaxaFreteBetween (BigDecimal taxaFreteMenor , BigDecimal taxaFreteMaior);

    List<Restaurante> consultarPorNome (String nome , @Param("id") Long cozinhaId);

//    List<Restaurante> findByNomeContainingAndCozinhaId (String nome , Long cozinhaId);

    Optional<Restaurante> findFirstByNomeContaining (String nome);

    List<Restaurante> findTop2ByNomeContaining (String nome) ;

    List<Restaurante> find (String nome , BigDecimal taxaInicial , BigDecimal taxaFinal) ;

    int countByCozinhaId (Long cozinhaId);
}
